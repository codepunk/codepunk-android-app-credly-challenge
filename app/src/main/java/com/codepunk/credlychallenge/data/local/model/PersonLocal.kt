package com.codepunk.credlychallenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.codepunk.credlychallenge.data.remote.model.ImagesRemote
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "person",
    indices = [
        Index("id"),
        Index("name")
    ]
)
data class PersonLocal(
    @PrimaryKey
    val id: Int,

    val url: String?,

    val name: String,

    val birthday: LocalDate?,

    @ColumnInfo(name = "death_day")
    val deathDay: LocalDate?,

    val gender: String?,

    val images: ImagesLocal?
)
