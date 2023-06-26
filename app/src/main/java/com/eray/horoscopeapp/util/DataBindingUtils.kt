package com.eray.horoscopeapp.util

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.size.Scale
import com.bumptech.glide.Glide
import com.google.common.math.Quantiles.scale

@BindingAdapter("app:imageUrl")
fun setImageUrl(view: AppCompatImageView, url: String?) {
    val circularProgressDrawable = CircularProgressDrawable(view.context)
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(view.context).load(url).placeholder(circularProgressDrawable).into(view)
}

@BindingAdapter("app:imageDrawable")
fun setImageDrawable(view: AppCompatImageView, drawable: Drawable?) {
    view.load(drawable) { scale(Scale.FILL) }
}