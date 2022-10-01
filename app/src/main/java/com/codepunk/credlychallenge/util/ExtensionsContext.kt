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

import android.content.Context
import android.widget.Toast
import com.codepunk.credlychallenge.R

/**
 * Shows a default error toast on the supplied [Context] using the supplied [Throwable].
 * If no throwable is supplied, displays a default error message.
 */
fun Context.showErrorToast(throwable: Throwable? = null) {
    val message = throwable?.message ?: getString(R.string.generic_error)
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
