package com.example.owd.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupsViewModel(private val groupsRepository: GroupsRepository) : ViewModel() {
    private val _groupsUIState = MutableStateFlow(GroupUIState())

    val groupsUIState: StateFlow<GroupUIState>
        get() = _groupsUIState

    init {
        fetchGroups()
    }

    private fun fetchGroups() {
        viewModelScope.launch {
            groupsRepository.getAllGroups().collect { groups ->
                _groupsUIState.value = GroupUIState(groups)
            }
        }
    }
}

data class GroupUIState(val groupList: List<Group> = emptyList())
