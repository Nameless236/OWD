package com.example.owd.data.groups

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(group: Group) : Long

    @Update
    suspend fun update(group: Group)

    @Delete
    suspend fun delete(group: Group)

    @Query("SELECT * from groups WHERE id = :id")
    fun getGroup(id: Int): Flow<Group>

    @Query("SELECT * from groups ORDER BY name ASC")
    fun getAllGroups(): Flow<List<Group>>
}