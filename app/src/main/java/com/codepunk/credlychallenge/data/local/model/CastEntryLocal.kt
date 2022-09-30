package com.codepunk.credlychallenge.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "cast",
    primaryKeys = ["person_id", "character_id"],
    foreignKeys = [
        ForeignKey(
            entity = PersonLocal::class,
            parentColumns = ["id"],
            childColumns = ["person_id"]
        ),
        ForeignKey(
            entity = CharacterLocal::class,
            parentColumns = ["id"],
            childColumns = ["character_id"]
        )
    ]
)
data class CastEntryLocal(
    @ColumnInfo(name = "person_id")
    val personId: Int,

    @ColumnInfo(name = "character_id")
    val characterId: Int,

    val self: Boolean?,

    val voice: Boolean?
)
