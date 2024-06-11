package com.example.owd.data.groups

import kotlinx.coroutines.flow.Flow

/**
 * Repository class responsible for managing groups data.
 * Implements [GroupsRepository] interface.
 *
 * @property groupDao Data Access Object (DAO) for performing CRUD operations on group entities.
 */
class GroupsOfflineRepository(private val groupDao: GroupDao) : GroupsRepository {

    /**
     * Inserts a new group into the database.
     *
     * @param group The group entity to insert.
     * @return The ID of the newly inserted group.
     */
    override suspend fun insert(group: Group): Long {
        return groupDao.insert(group)
    }

    /**
     * Updates an existing group in the database.
     *
     * @param group The group entity to update.
     */
    override suspend fun update(group: Group) = groupDao.update(group)

    /**
     * Deletes a group from the database.
     *
     * @param group The group entity to delete.
     */
    override suspend fun delete(group: Group) = groupDao.delete(group)

    /**
     * Retrieves a group from the database based on its ID.
     *
     * @param id The ID of the group to retrieve.
     * @return A flow representing the group with the specified ID.
     */
    override fun getGroup(id: Int): Flow<Group> = groupDao.getGroup(id)

    /**
     * Retrieves all groups from the database.
     *
     * @return A flow representing a list of all groups in the database.
     */
    override fun getAllGroups(): Flow<List<Group>> = groupDao.getAllGroups()
}
