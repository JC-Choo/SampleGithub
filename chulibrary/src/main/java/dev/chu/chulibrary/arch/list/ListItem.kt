package dev.chu.chulibrary.arch.list

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * [BaseAdapter]상에서 사용될 데이터 리스트용 인터페이스
 *
 * - [BaseAdapter.list]는 [ListItem]을 구현한 클래스만 사용한다.
 * - [DiffUtil.ItemCallback]에서 사용될 [isItemSame], [isContentsSame]을 제공한다.
 *
 * @see [ListItemDiffCallback].
 */
interface ListItem<in T : Any> {

    /**
     * 자신과 [target]이 같은 아이템인지 체크한다.
     *
     * - 필요에 따라 override 한다.
     * - 만약 다른 아이템이면 해당 [ViewHolder]에 새로 바인딩 된다.
     * - 같은 아이템이면 [isContentsSame] 결과에 따라 새로 바인딩 여부를 결정한다.
     */
    fun isItemSame(target: T): Boolean {
        return this == target
    }

    /**
     * 자신과 [target]이 같은 아이템인 경우, 내용이 같은지 확인한다.
     *
     * - 필요에 따라 override 한다.
     * - true 를 리턴하면 해당 [ViewHolder]는 새로 바인딩이 되지 않는다.
     * - 내용이 변경되었으나 true 를 리턴하면 실제 UI는 업데이트가 되지 않으므로 주의 필요.
     */
    fun isContentsSame(target: T): Boolean {
        return false
    }
}