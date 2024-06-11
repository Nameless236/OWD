package com.example.owd.data.persons

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing persons.
 */
interface PersonsRepository {
    /**
     * Inserts a person into the repository.
     *
     * @param person The person to insert.
     */
    suspend fun insert(person: Person)

    /**
     * Updates a person in the repository.
     *
     * @param person The person to update.
     */
    suspend fun update(person: Person)

    /**
     * Deletes a person from the repository.
     *
     * @param person The person to delete.
     */
    suspend fun delete(person: Person)

    /**
     * Retrieves a person by its group ID.
     *
     * @param groupId The ID of the group.
     * @return A [Flow] emitting the person with the specified group ID.
     */
    fun getPerson(groupId: Int): Flow<Person?>

    /**
     * Retrieves all persons in the repository.
     *
     * @return A [Flow] emitting a list of all persons in the repository.
     */
    fun getAllPersons(): Flow<List<Person?>>

    /**
     * Retrieves all persons belonging to a specific group.
     *
     * @param groupId The ID of the group.
     * @return A [Flow] emitting a list of persons belonging to the specified group.
     */
    fun getPersonsGroup(groupId: Int): Flow<List<Person>>
}
