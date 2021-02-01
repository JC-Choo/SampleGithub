package dev.chu.chulibrary.arch.list

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dev.chu.chulibrary.util.extensions.findActivity

/**
 *
 */
abstract class BaseViewHolder<Item: ListItem<*>>(
    view: View
) : RecyclerView.ViewHolder(view) {

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
}