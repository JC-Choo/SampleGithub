package dev.chu.chulibrary.arch.list

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter(

) : RecyclerView.Adapter<BaseViewHolder<ListItem<*>>>(), ListUpdateCallback {

    /**
     * [BaseAdapter]가 현재 사용중인 item list.
     */
//    var list: List<ListItem<*>>?
//        get() = differ.currentList
//        set(value) {
//            _isListUpdateNotified.changeValue(Event(false))
//            latestList = value
//            val newList = if (isLoadMoreAdded) {
//                getListWithLoadMore()
//            } else {
//                value
//            }
//            differ.submitList(newList)
//        }

    /**
     * [BaseAdapter.list]의 내부 변수.
     * - pagination 이용시 pagination loading UI 표현을 위해, [BaseAdapter.list]에 [LoadMore]이 추가/삭제 되어야 한다.
     * - 해당 필드를 이용해 [LoadMore]추가/삭제 여부에 따른 [BaseAdapter.list]를 생성한다.
     *
     * @see BaseAdapter.getListWithLoadMore
     * @see BaseAdapter.removeLoadMore
     */
    private var latestList: List<ListItem<*>>? = null

    /**
     * [BaseAdapter.list]의 변경 이전/이후 비교를 통해 변경 부분만 update 할수 있도록 돕는 [AsyncListDiffer]
     *
     * @see ListItemDiffCallback
     */
    private val differ: AsyncListDiffer<ListItem<*>> by lazy {
        AsyncListDiffer(this, AsyncDifferConfig.Builder(ListItemDiffCallback()).build())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItem<*>> {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ListItem<*>>, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onInserted(position: Int, count: Int) {
        TODO("Not yet implemented")
    }

    override fun onRemoved(position: Int, count: Int) {
        TODO("Not yet implemented")
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        TODO("Not yet implemented")
    }
}