package dev.chu.chulibrary.arch.list

/**
 * [BaseAdapter]에서 [RecyclerView.ViewHolder] 생성 시 사용하는 Factory
 * - register 하는 [BaseViewHolder]와 이에 bind 되는 [ListItem]을 [ViewHolderMapper]로 wrapping 하여 관리한다.
 * - DSL 형태로 set 할 수 있도록 [ViewHolderFactory.map]을 이용한다.
 *
 * @see viewHolderFactory
 * ------------------------------------------------------------------------------
 * viewHolderFactory {
 *      map { OneViewHolder(it) }
 *      map { TwoViewHolder(it) }
 *      map({ it.viewType == 1 }) { FirstViewHolder(it) }
 *      map({ it.viewType == 2 }) { SecondViewHolder(it) }
 *      ...
 * }
 * ------------------------------------------------------------------------------
 */
class ViewHolderFactory internal constructor() {
    /**
     * [ViewHolderFactory]에 register 한 [RecyclerView.ViewHolder] 리스트
     */
//    private val mappers = MutableList<ViewHolderMapper>()


}