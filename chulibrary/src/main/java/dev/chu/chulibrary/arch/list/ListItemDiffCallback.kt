package dev.chu.chulibrary.arch.list

import androidx.recyclerview.widget.DiffUtil

/**
 * [BaseAdapter]가 연결된 [androidx.recyclerview.widget.RecyclerView]의 성능 향상을 위한 [DiffUtil.ItemCallback] 구현 클래스.
 *
 * - [androidx.recyclerview.widget.AsyncListDiffer]에서 [ListItem] 비교를 수행한다.
 */
@Suppress("UNCHECKED_CAST")
internal class ListItemDiffCallback : DiffUtil.ItemCallback<ListItem<*>>() {

    override fun areItemsTheSame(oldItem: ListItem<*>, newItem: ListItem<*>): Boolean {
        if (areSameType(oldItem, newItem)) {
            return (oldItem as ListItem<Any>).isItemSame(newItem as ListItem<Any>)
        }
        return false
    }

    override fun areContentsTheSame(oldItem: ListItem<*>, newItem: ListItem<*>): Boolean {
        if (areSameType(oldItem, newItem)) {
            return (oldItem as ListItem<Any>).isContentSame(newItem as ListItem<Any>)
        }

        return false
    }

    private fun areSameType(oldItem: ListItem<*>, newItem: ListItem<*>): Boolean =
        oldItem::class == newItem::class
            return (oldItem as ListItem<Any>).isContentsSame(newItem as ListItem<Any>)
        }
        return false
    }

    private fun areSameType(oldItem: ListItem<*>, newItem: ListItem<*>): Boolean {
        return oldItem::class == newItem::class
    }
}