package com.codepunk.credlychallenge.domain.model

data class Character(
    val id: Int,
    val url: String?,
    val name: String,
    val images: Images?
)
