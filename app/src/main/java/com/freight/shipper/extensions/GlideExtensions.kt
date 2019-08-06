package com.freight.shipper.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("imageUrl")
fun ImageView.load(str: String?) {
    Glide.with(this)
        .load(str)
        .into(this)
}

@BindingAdapter("loadDrawable")
fun ImageView.setImageDrawable(drawable: Drawable?) {
    drawable?.also { this.setImageDrawable(drawable) }
}

fun ImageView.loadCircularImage(str: String?) {
    Glide.with(this)
        .load(str)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.unload() {
    Glide.with(this).clear(this)
}
