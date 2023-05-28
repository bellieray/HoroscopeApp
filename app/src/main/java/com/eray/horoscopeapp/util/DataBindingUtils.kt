package com.eray.horoscopeapp.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.size.Scale

@BindingAdapter("app:imageUrl")
fun setImageUrl(view: AppCompatImageView, url: String?) {
    view.load(url) { scale(Scale.FILL) }
}