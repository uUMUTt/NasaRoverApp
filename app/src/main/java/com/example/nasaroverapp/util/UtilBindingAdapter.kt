package com.example.nasaroverapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("android:loadImageToRow")
fun loadImageToRow(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        view.loadImageWithGlide(it)
    }
}
