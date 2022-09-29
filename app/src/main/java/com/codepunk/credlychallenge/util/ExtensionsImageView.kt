package com.codepunk.credlychallenge.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.codepunk.credlychallenge.R

private const val MEDIUM = "medium"

@BindingAdapter("bindThumbnail")
fun ImageView.bindThumbnail(images: Map<String, String>?) {
    when (val imageUrl = images?.get(MEDIUM)) {
        null -> setImageResource(R.drawable.default_thumbnail)
        else -> load(imageUrl)
    }
}