package com.gutotech.dogs.util

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gutotech.dogs.R

fun ImageView.loadImage(url: String) {
    val requestOptions = RequestOptions()
        .placeholder(CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        })
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .into(this)
}