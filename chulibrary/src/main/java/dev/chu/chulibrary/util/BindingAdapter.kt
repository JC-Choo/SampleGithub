package dev.chu.chulibrary.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageLoad")
fun ImageView.setImageLoad(drawable: Drawable) {
    Glide.with(this)
        .load(drawable)
        .into(this)
}