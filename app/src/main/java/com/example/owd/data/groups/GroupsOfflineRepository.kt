package com.example.owd.data.groups

import kotlinx.coroutines.flow.Flow

class GroupsOfflineRepository(private val groupDao: GroupDao) : GroupsRepository {
    override suspend fun insert(group: Group): Long {
        return groupDao.insert(group)
    }

    override suspend fun update(group: Group) = groupDao.update(group)

    override suspend fun delete(group: Group) = groupDao.delete(group)

    override fun getGroup(id: Int): Flow<Group> = groupDao.getGroup(id)

    override fun getAllGroups(): Flow<List<Group>> = groupDao.getAllGroups()
}