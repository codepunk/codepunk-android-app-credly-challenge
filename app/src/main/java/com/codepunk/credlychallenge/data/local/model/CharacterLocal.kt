package com.codepunk.credlychallenge.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "character",
    indices = [
        Index("id"),
        Index("name")
    ]
)
data class CharacterLocal(
    @PrimaryKey
    val id: Int,

    val url: String?,

    val name: String,

    val images: ImagesLocal?
)
