package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.ImagesLocal
import com.codepunk.credlychallenge.data.remote.model.ImagesRemote
import com.codepunk.credlychallenge.domain.model.Images

fun ImagesRemote.toDomainModel(): Images = Images(medium, original)

fun Images.toLocalModel(): ImagesLocal = ImagesLocal(medium, original)

fun ImagesLocal.toDomainModel(): Images = Images(medium, original)