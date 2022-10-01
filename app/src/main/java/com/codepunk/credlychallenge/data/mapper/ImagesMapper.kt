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

package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.ImagesLocal
import com.codepunk.credlychallenge.data.remote.model.ImagesRemote
import com.codepunk.credlychallenge.domain.model.Images

/**
 * A set of methods used to map between remote, local, and domain image URLs.
 */

fun ImagesRemote.toDomainModel(): Images = Images(medium, original)

fun Images.toLocalModel(): ImagesLocal = ImagesLocal(medium, original)

fun ImagesLocal.toDomainModel(): Images = Images(medium, original)
