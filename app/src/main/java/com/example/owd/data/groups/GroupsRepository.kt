package com.example.owd.data.groups

import kotlinx.coroutines.flow.Flow

interface GroupsRepository {
    suspend fun insert(group: Group) : Long

    suspend fun update(group: Group)

    suspend fun delete(group: Group)

    fun getGroup(id: Int): Flow<Group?>

    fun getAllGroups(): Flow<List<Group>>
}