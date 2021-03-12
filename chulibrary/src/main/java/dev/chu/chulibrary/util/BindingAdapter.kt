package dev.chu.chulibrary.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import dev.chu.chulibrary.R

@BindingAdapter("setImageLoad")
fun ImageView.setImageLoad(drawable: Drawable?) {
    if (drawable != null) {
        Glide.with(this)
            .load(drawable)
            .into(this)
    }
}

@BindingAdapter("isVisible")
fun View.setViewVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}