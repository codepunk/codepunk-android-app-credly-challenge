package com.codepunk.credlychallenge.data.local.dao

import androidx.room.*
import com.codepunk.credlychallenge.data.local.model.PersonLocal

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: PersonLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(people: List<PersonLocal>)

    @Delete
    suspend fun delete(person: PersonLocal)

    @Query("SELECT * FROM person WHERE id=:id")
    suspend fun getPerson(id: Int): PersonLocal?

    @Query("SELECT * FROM person INNER JOIN `cast` ON person.id = `cast`.person_id WHERE `cast`.show_id = :showId")
    suspend fun getPeopleInShow(showId: Int): List<PersonLocal>

}