package dev.chu.chulibrary.arch.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import dev.chu.chulibrary.util.extensions.findActivity

/**
 * [RecyclerView]에 사용할 base [RecyclerView.ViewHolder]
 * - [layoutRes]에 해당하는 view 를 만든다.
 * - [bindItem]을 override 하여 사용한다.
 * - null 이 binding 되는 경우, default 로 빈 layout 을 내려준다.
 * - null 에 대한 추가적인 처리가 필요한 경우, [bindItemNullable] 을 override 한다.
 * - attach/detach, scroll, recycle 과 같은 이벤트 시점에 처리해야 할 로직 필요 시, 각 함수들을 override 한다.
 */
abstract class BaseViewHolder<Item : ListItem<*>> : RecyclerView.ViewHolder {

    constructor(itemView: View) : super(itemView)
    constructor(
        parent: ViewGroup,
        @LayoutRes layoutRes: Int
    ) : this(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))

    abstract fun bind(item: Item)

    /**
     * [RecyclerView.ViewHolder]에서 사용할 context
     */
    protected val context: Context
        get() = itemView.context

    /**
     * [RecyclerView.ViewHolder]에서 사용할 activity
     */
    protected val activity: AppCompatActivity?
        get() = context.findActivity()

    /**
     * bind 된 [Item]
     */
    protected lateinit var item: Item

    /**
     * [BaseViewHolder] 내부에 [RecyclerView]를 가지는 경우 해당 [RecyclerView]의 scroll 상태를 유지(저장/복원)한다.
     */
    protected lateinit var scrollStateHolder: BaseScrollStateHolder

    /**
     * [BaseViewHolder]에 실제 [Item]을 바인딩한다.
     * @see BaseAdapter.onBindViewHolder
     */
    abstract fun bindItem(item: Item)

    /**
     * [BaseViewHolder] 생성 직후 1회성 초기화가 필요할 때, 이 함수를 override
     */
    @CallSuper
    open fun onCreated() {
        if (this is BaseScrollStateHolder.ScrollStateProvider) {
            scrollStateHolder.setUpRecyclerView(this)

            if (this is BaseScrollStateHolder.SnapProvider) {
                this.snapHelper.attachToRecyclerView(this.nestedRecyclerView)
            }
        }
    }

    /**
     * [item]이 null 인 경우에 대한 별도의 처리가 필요할 경우, 이 함수를 override
     */
    open fun bindItemNullable(item: Item?) {
        if (item == null) {
            itemView.isGone = true
            return
        }

        if (!isSameBindItem(item)) {
            this.item = item
            bindItem(item)
            if (this is BaseScrollStateHolder.ScrollStateProvider) {
                scrollStateHolder.restoreScrollState(this)
            }
        }
    }

    /**
     * [BaseViewHolder]의 재활용 시점에 [BaseViewHolder.item]과 [item]을 비교한다.
     * 동일한 경우 [bindItem]의 구현부에서 필요에 따라 갱신 회피 등의 클라이언트 로직을 적용하도록 한다.
     * @see bindItemNullable
     *
     * == vs ===
     * Java 에서는 원시 타입을 비교하기 위해 == 사용. 즉, 값의 비교. ex) int a = 2, int b = 3 -> a == b -> false
     * 그러나 참조 타입의 경우 == 사용 시 주소값을 비교 ex) String a = "a"; String b = "a" -> a == b -> false
     * 따라서 참조 타입을 비교하고 싶은 경우 .equals 를 사용한다.
     *
     * Kotlin 에서 역시 원시 타입 비교 시 == 를 사용하며, 참조 타입의 값 비교 시 내부적으로 == 것이 .equals 로 변해 비교한다.
     * 그러나 참조 타입의 주소 값을 비교하고 싶다면, === 를 사용해야 한다. (자바의 == 와 같은 역할)
     */
    private fun isSameBindItem(item: Item): Boolean =
        this::item.isInitialized && this.item === item

    /**
     * [BaseViewHolder] 가 window 에 attach 시 호출
     * @see BaseAdapter.onViewAttachedToWindow
     */
    open fun onAttachToWindow() { /* nothing */ }

    /**
     * [BaseViewHolder]가 window 로부터 detach 시 호출
     * @see BaseAdapter.onViewDetachedFromWindow
     */
    open fun onDetachedFromWindow() {
        if (this is BaseScrollStateHolder.SnapProvider &&
                nestedRecyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
            nestedRecyclerView.layoutManager?.let { layoutManager ->
                snapHelper.findSnapView(layoutManager)?.let { view ->
                    val snapDistance = snapHelper.calculateDistanceToFinalSnap(layoutManager, view)
                    if (snapDistance!![0] != 0 || snapDistance[1] != 0) {
                        nestedRecyclerView.scrollBy(snapDistance[0], snapDistance[1])
                    }
                }
            }
        }
    }

    /**
     * [RecyclerView]가 scroll(onScrolled)될 때 호출
     * @see BaseAdapter.onScrollListener
     */
    open fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) { /* nothing */ }

    /**
     * [RecyclerView]의 scroll 상태가 변경(onScrollStateChanged)될 때 호출
     * @see BaseAdapter.onScrollListener
     */
    open fun onScrollStateChanged(rv: RecyclerView?, newState: Int) { /* nothing */ }

    /**
     * [BaseViewHolder]가 recycle 될 시 호출
     * @see BaseAdapter.onViewRecycled
     */
    @CallSuper
    open fun onRecycled() {
        if (this is BaseScrollStateHolder.ScrollStateProvider) {
            scrollStateHolder.saveScrollState(this)
        }
    }
}