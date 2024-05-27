package com.example.owd.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GroupsViewModel(groupsRepository: GroupsRepository) : ViewModel() {
    val groupsUIState: StateFlow<GroupUIState> =
        groupsRepository.getAllGroups().map { GroupUIState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GroupUIState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GroupUIState(val groupList: List<Group> = listOf())