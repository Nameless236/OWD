package com.example.owd.data.persons

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing persons in the database.
 */
@Dao
interface PersonDao {
    /**
     * Inserts a person into the database.
     *
     * @param person The person to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(person: Person)

    /**
     * Updates a person in the database.
     *
     * @param person The person to update.
     */
    @Update
    suspend fun update(person: Person)

    /**
     * Deletes a person from the database.
     *
     * @param person The person to delete.
     */
    @Delete
    suspend fun delete(person: Person)

    /**
     * Retrieves a person by its group ID.
     *
     * @param groupId The ID of the group.
     * @return A [Flow] emitting the person with the specified group ID.
     */
    @Query("SELECT * from persons WHERE groupId = :groupId")
    fun getPerson(groupId: Int): Flow<Person?>

    /**
     * Retrieves all persons in the database.
     *
     * @return A [Flow] emitting a list of all persons in the database.
     */
    @Query("SELECT * from persons ORDER BY name ASC")
    fun getAllPersons(): Flow<List<Person?>>

    /**
     * Retrieves all persons belonging to a specific group.
     *
     * @param groupId The ID of the group.
     * @return A [Flow] emitting a list of persons belonging to the specified group.
     */
    @Query("SELECT * from persons WHERE groupId = :groupId ORDER BY name ASC")
    fun getPersonsGroup(groupId: Int): Flow<List<Person>>
}
