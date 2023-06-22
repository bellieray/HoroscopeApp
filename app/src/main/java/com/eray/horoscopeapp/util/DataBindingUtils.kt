package com.eray.horoscopeapp.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

@BindingAdapter("app:imageUrl")
fun setImageUrl(view: AppCompatImageView, url: String?) {
    val circularProgressDrawable = CircularProgressDrawable(view.context)
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(view.context).load(url).placeholder(circularProgressDrawable).into(view)
}