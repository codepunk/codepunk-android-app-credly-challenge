package com.codepunk.credlychallenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "show",
    indices = [
        Index("id"),
        Index("name")
    ]
)
data class ShowLocal(
    @PrimaryKey
    val id: Int,

    val name: String,

    val url: String?,

    val type: String?,

    val language: String?,

    val genres: List<String>?,

    val status: String?,

    val runtime: Int?,

    @ColumnInfo(name = "average_runtime")
    val averageRuntime: Int?,

    val premiered: LocalDate?,

    val ended: LocalDate?,

    @ColumnInfo(name = "official_site")
    val officialSite: String?,

    val images: ImagesLocal?,

    val summary: String?
)
