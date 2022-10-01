/*
 * Copyright (C) 2022 Scott Slater
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.codepunk.credlychallenge.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.codepunk.credlychallenge.R
import com.codepunk.credlychallenge.domain.model.Images

/**
 * Binds a thumbnail image to an [ImageView].
 */
@BindingAdapter("bindThumbnail")
fun ImageView.bindThumbnail(images: Images?) {
    when (val imageUrl = images?.medium) {
        null -> setImageResource(R.drawable.default_thumbnail)
        else -> load(imageUrl)
    }
}

/**
 * Binds a header image to an [ImageView].
 */
@BindingAdapter("bindHeaderImage")
fun ImageView.bindHeaderImage(images: Images?) {
    when (val imageUrl = images?.original) {
        null -> setImageDrawable(null)
        else -> load(imageUrl)
    }
}
