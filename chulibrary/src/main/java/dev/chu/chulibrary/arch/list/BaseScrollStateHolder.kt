package dev.chu.chulibrary.arch.list

import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

/**
 * 중첩된 [RecyclerView]의 scroll state 유지를 위한 class.
 * - [RecyclerView.Adapter.onViewRecycled]에서 [saveScrollState]를 호출하며 스크롤 위치에 저장
 * - [RecyclerView.Adapter.onBindViewHolder]시점 이후 [restroreScrollState]를 호출하여 스크롤 위치를 복원
 */
class BaseScrollStateHolder(
    savedInstanceState: Bundle?
) {

    /**
     * 중첩 대상이 되는 [nestedRecyclerView]와 이를 식별하는 키 [getScrollStateKey]를 제공하는 interface
     */
    interface ScrollStateProvider {
        val nestedRecyclerView: RecyclerView
        fun getScrollStateKey(): String?
    }

    /**
     * 중첩 대상이 되는 [nestedRecyclerView]가 [SnapHelper]를 사용할 경우 이를 제공하는 interface
     */
    interface SnapProvider: ScrollStateProvider {
        val snapHelper: SnapHelper
    }

    companion object {
        const val STATE_BUNDLE = "STATE_BUNDLE"
    }

    /**
     * 중첩 대상이 되는 [RecyclerView]의 상태 복원을 위한 [RecyclerView.LayoutManager] 상태를 저장할 변수
     */
    private val scrollStates = hashMapOf<String, Parcelable>()

    /**
     * 상태 복원 대상이 되는 [RecyclerView]를 식별하는 키 값을 저장할 변수
     * - 해당 set 내에 키가 존재할 경우 스크롤 상태를 복원시킨다.
     */
    private val scrolledKeys = mutableSetOf<String>()

    /**
     * Activity or Fragment 재생성시 이전에 저장한 상태 값이 존재할 경우 해당 값을 가져온다.
     *
     * - Activity or Fragment 재생성시 중첩 스크롤 상태 복원을 위해
     * [BaseScrollStateHolder.onSaveInstanceState]를 통해 savedInstanceState 를 저장 한 경우
     * Activity or Fragment 의 onCreate 시점에 전달 받은 savedInstanceState 를
     * [BaseScrollStateHolder] 생성시 생성자 변수로 전달 하도록 한다.
     */
    init {
        savedInstanceState?.getBundle(STATE_BUNDLE)?.let { bundle ->
            bundle.keySet().forEach { key ->
                bundle.getParcelable<Parcelable>(key)?.let {
                    scrollStates[key] = it
                }
            }
        }
    }

    /**
     * [ScrollStateProvider]를 통해 제공받는 중첩 대상 [RecyclerView]의 상태 유지를 위한 초기화 작업을 수행
     */
    fun setUpRecyclerView(scrollProvider: ScrollStateProvider) {
        scrollProvider.nestedRecyclerView.isNestedScrollingEnabled = false
        scrollProvider.nestedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    saveScrollState(scrollProvider)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val key = scrollProvider.getScrollStateKey()
                if (key != null && dx != 0) {
                    scrolledKeys.add(key)
                }
            }
        })
    }

    /**
     * [androidx.appcompat.app.AppCompatActivity] or [androidx.fragment.app.Fragment] 재생성 시점에
     * 스크롤 상태 복원이 필요할 경우, 해당 함수를 호출
     *
     * @param outState : [androidx.appcompat.app.AppCompatActivity.onSaveInstanceState] or
     * [androidx.fragment.app.Fragment.onSaveInstanceState] 호출 시점에 주입되는 [outState]
     */
    fun onSaveInstanceState(outState: Bundle) {
        val stateBundle = Bundle()
        scrollStates.entries.forEach {
            stateBundle.putParcelable(it.key, it.value)
        }
        outState.putBundle(STATE_BUNDLE, stateBundle)
    }

    /**
     * 저장된 [scrollStates] 와 [scrolledKeys] 삭제
     * - 부모 [RecyclerView] 전체 상태를 리셋하거나, 복원 상태를 초기화하고 싶을 경우 해당 함수를 호출
     */
    fun clearScrollState() {
        scrollStates.clear()
        scrolledKeys.clear()
    }

    /**
     * 중첩 대상이 되는 [RecyclerView]의 레이아웃 상태를 저장
     */
    fun saveScrollState(scrollProvider: ScrollStateProvider) {
        val key = scrollProvider.getScrollStateKey() ?: return
        if (scrolledKeys.contains(key)) {
            val layoutManager = scrollProvider.nestedRecyclerView.layoutManager ?: return
            layoutManager.onSaveInstanceState()?.let {
                scrollStates[key] = it
            }
            scrolledKeys.remove(key)
        }
    }

    /**
     * 중첩 대상이 되는 [RecyclerView]의 레이아웃 상태를 복원
     */
    fun restoreScrollState(scrollProvider: ScrollStateProvider) {
        val key = scrollProvider.getScrollStateKey() ?: return
        val lm = scrollProvider.nestedRecyclerView.layoutManager ?: return
        val savedState = scrollStates[key]
        if (savedState != null) {
            lm.onRestoreInstanceState(savedState)
        } else {
            lm.scrollToPosition(0)
        }

        // 복원 후 복원 대상에서 삭제
        scrolledKeys.remove(key)
    }
}