package com.example.owd.data.groups

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for performing CRUD operations on group entities.
 * Defines methods for inserting, updating, deleting, and querying group data from the database.
 */
@Dao
interface GroupDao {

    /**
     * Inserts a new group into the database.
     *
     * @param group The group entity to insert.
     * @return The ID of the newly inserted group.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(group: Group): Long

    /**
     * Updates an existing group in the database.
     *
     * @param group The group entity to update.
     */
    @Update
    suspend fun update(group: Group)

    /**
     * Deletes a group from the database.
     *
     * @param group The group entity to delete.
     */
    @Delete
    suspend fun delete(group: Group)

    /**
     * Retrieves a group from the database based on its ID.
     *
     * @param id The ID of the group to retrieve.
     * @return A flow representing the group with the specified ID.
     */
    @Query("SELECT * from groups WHERE id = :id")
    fun getGroup(id: Int): Flow<Group>

    /**
     * Retrieves all groups from the database, sorted alphabetically by name.
     *
     * @return A flow representing a list of all groups in the database.
     */
    @Query("SELECT * from groups ORDER BY name ASC")
    fun getAllGroups(): Flow<List<Group>>
}
