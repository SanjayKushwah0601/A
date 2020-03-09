package com.freight.shipper.extensions

import android.graphics.drawable.Drawable
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
        .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_default_user))
        .into(this)
}

@BindingAdapter(value = ["url", "placeholder"], requireAll = false)
fun ImageView.loadCircularProfile(url: String?, placeholder: Drawable? = null) {
    val option = if (placeholder == null) RequestOptions.circleCropTransform()
    else RequestOptions.circleCropTransform().placeholder(placeholder)
    Glide.with(this)
        .load(url)
        .apply(option)
        .into(this)
}

fun ImageView.unload() {
    Glide.with(this).clear(this)
}
