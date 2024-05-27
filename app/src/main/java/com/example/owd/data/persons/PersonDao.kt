package com.example.owd.data.persons

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(person: Person)

    @Update
    suspend fun update(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query("SELECT * from persons WHERE groupId = :groupId")
    fun getPerson(groupId: Int): Flow<Person?>

    @Query("SELECT * from persons ORDER BY name ASC")
    fun getAllPersons(): Flow<List<Person?>>
}