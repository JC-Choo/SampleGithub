package dev.chu.chulibrary.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.Placeholder
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import dev.chu.chulibrary.R

@BindingAdapter("setVisible")
fun setVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("setInvisible")
fun setInvisible(view: View, isInvisible: Boolean) {
    view.isInvisible = isInvisible
}

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
fun ImageView.setImageUrl(imageUrl: String?, placeholder: Drawable?) {
    val placeholderDrawable = placeholder ?: AppCompatResources.getDrawable(context, R.drawable.generic_placeholder) as Drawable
    when (imageUrl.isNullOrEmpty()) {
        true -> {
            Glide.with(this).load(imageUrl).into(this)
        }
        false -> {
            Glide.with(this).load(placeholderDrawable).into(this)
        }
    }
}