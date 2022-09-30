package com.codepunk.credlychallenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "episode",
    foreignKeys = [
        ForeignKey(
            entity = ShowLocal::class,
            parentColumns = ["id"],
            childColumns = ["show_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("id"),
        Index("name")
    ]
)
data class EpisodeLocal(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "show_id")
    val showId: Int,

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
