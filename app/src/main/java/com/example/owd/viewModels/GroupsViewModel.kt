package com.example.owd.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing groups data.
 *
 * @property groupsRepository Repository for accessing groups data.
 */
class GroupsViewModel(private val groupsRepository: GroupsRepository) : ViewModel() {
    private val _groupsUIState = MutableStateFlow(GroupUIState())

    /**
     * Represents the state of groups UI.
     */
    val groupsUIState: StateFlow<GroupUIState>
        get() = _groupsUIState

    /**
     * Initializes the ViewModel by fetching groups.
     */
    init {
        fetchGroups()
    }

    /**
     * Fetches groups from the repository.
     */
    private fun fetchGroups() {
        viewModelScope.launch {
            groupsRepository.getAllGroups().collect { groups ->
                _groupsUIState.value = GroupUIState(groups)
            }
        }
    }
}

/**
 * Represents the UI state of groups.
 *
 * @property groupList List of groups.
 */
data class GroupUIState(val groupList: List<Group> = emptyList())
