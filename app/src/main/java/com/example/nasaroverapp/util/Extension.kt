package com.example.nasaroverapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageWithGlide(imageUrl: String?) {
    imageUrl?.let {
        val newUrl = it.replace("http", "https")
        Glide.with(context).load(newUrl).into(this)
    }
}