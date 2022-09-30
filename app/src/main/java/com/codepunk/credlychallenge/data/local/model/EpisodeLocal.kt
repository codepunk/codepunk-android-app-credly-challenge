package com.codepunk.credlychallenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.codepunk.credlychallenge.data.remote.model.ImagesRemote
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "episode",
    indices = [
        Index("id"),
        Index("name")
    ]
)
data class EpisodeLocal(
    @PrimaryKey
    val id: Int,

    val url: String,

    val name: String,

    val season: Int,

    val number: Int,

    @ColumnInfo(name = "airdate")
    val airDate: LocalDate,

    val runtime: Int,

    val images: ImagesLocal?,

    val summary: String?
)
