package com.freight.shipper.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.freight.shipper.R


@BindingAdapter("imageUrl")
fun ImageView.load(str: String?) {
    Glide.with(this)
        .load(str)
        .into(this)
}

@BindingAdapter("loadDrawable")
fun ImageView.setImageDrawableGlide(drawable: Drawable?) {
    drawable?.also { this.setImageDrawable(drawable) }
}

@BindingAdapter("loadCircularImage")
fun ImageView.loadCircularImage(str: String?) {
    Glide.with(this)
        .load(str)
        .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.round_gray).error(R.drawable.round_gray))
        .into(this)
}

fun ImageView.unload() {
    Glide.with(this).clear(this)
}
