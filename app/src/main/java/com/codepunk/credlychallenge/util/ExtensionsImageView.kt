package com.codepunk.credlychallenge.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.codepunk.credlychallenge.R
import com.codepunk.credlychallenge.domain.model.Images

@BindingAdapter("bindThumbnail")
fun ImageView.bindThumbnail(images: Images?) {
    when (val imageUrl = images?.medium) {
        null -> setImageResource(R.drawable.default_thumbnail)
        else -> load(imageUrl)
    }
}