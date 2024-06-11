package com.example.owd.data.groups

import kotlinx.coroutines.flow.Flow

/**
 * Interface defining operations for managing groups data.
 */
interface GroupsRepository {
    /**
     * Inserts a new group into the data source.
     *
     * @param group The group entity to insert.
     * @return The ID of the newly inserted group.
     */
    suspend fun insert(group: Group): Long

    /**
     * Updates an existing group in the data source.
     *
     * @param group The group entity to update.
     */
    suspend fun update(group: Group)

    /**
     * Deletes a group from the data source.
     *
     * @param group The group entity to delete.
     */
    suspend fun delete(group: Group)

    /**
     * Retrieves a group from the data source based on its ID.
     *
     * @param id The ID of the group to retrieve.
     * @return A flow representing the group with the specified ID.
     */
    fun getGroup(id: Int): Flow<Group>

    /**
     * Retrieves all groups from the data source.
     *
     * @return A flow representing a list of all groups in the data source.
     */
    fun getAllGroups(): Flow<List<Group>>
}
