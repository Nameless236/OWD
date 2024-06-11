package com.example.owd.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupsRepository
import com.example.owd.data.persons.Person
import com.example.owd.data.persons.PersonsRepository

/**
 * ViewModel for managing the process of adding a new group.
 * @property groupsRepository Repository for managing group data.
 * @property personsRepository Repository for managing person data.
 */
class AddGroupViewModel(
    private val groupsRepository: GroupsRepository,
    private val personsRepository: PersonsRepository
) : ViewModel() {

    // Mutable state for the UI state of adding a new group
    var groupUIState by mutableStateOf(NewGroupUiState())
        private set

    // Mutable state for the name of a new member being added
    var newMemberName by mutableStateOf("")

    /**
     * Saves the new group to the database if the input is valid.
     */
    suspend fun saveItem() {
        if (validateInput(groupUIState.groupDetails)) {
            val id = groupsRepository.insert(groupUIState.groupDetails.toItem())
            groupUIState.groupDetails.members.forEach { member ->
                personsRepository.insert(Person(name = member, groupId = id))
            }
        }
    }

    /**
     * Updates the UI state with the provided group details and validates the input.
     * @param groupDetails Details of the group being added.
     */
    fun updateUiState(groupDetails: GroupDetails) {
        groupUIState = NewGroupUiState(
            groupDetails = groupDetails,
            isEntryValid = validateInput(groupDetails)
        )
    }

    /**
     * Validates the input for adding a new group.
     * @param uiState UI state containing the group details.
     * @return `true` if the input is valid, `false` otherwise.
     */
    private fun validateInput(uiState: GroupDetails): Boolean {
        return uiState.name.isNotBlank() && uiState.members.isNotEmpty()
    }

    /**
     * Adds a new member to the group being added.
     */
    fun addMember() {
        groupUIState = groupUIState.copy(
            groupDetails = groupUIState.groupDetails.copy(
                members = groupUIState.groupDetails.members + newMemberName
            )
        )
        newMemberName = ""
    }

    /**
     * Updates the description of the group being added.
     * @param description New description for the group.
     */
    fun updateDescription(description: String) {
        groupUIState = groupUIState.copy(
            groupDetails = groupUIState.groupDetails.copy(description = description)
        )
    }

    /**
     * Updates the name of the group being added.
     * @param name New name for the group.
     */
    fun updateName(name: String) {
        groupUIState = groupUIState.copy(
            groupDetails = groupUIState.groupDetails.copy(name = name)
        )
    }
}

/**
 * UI state for adding a new group.
 * @property groupDetails Details of the group being added.
 * @property isEntryValid `true` if the input is valid, `false` otherwise.
 */
data class NewGroupUiState(
    val groupDetails: GroupDetails = GroupDetails(),
    val isEntryValid: Boolean = false
)

/**
 * Details of a group being added.
 * @property id Unique identifier of the group.
 * @property name Name of the group.
 * @property description Description of the group.
 * @property members List of members in the group.
 */
data class GroupDetails(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val members: List<String> = listOf()
)

/**
 * Converts group details to a group item.
 * @return Group item.
 */
fun GroupDetails.toItem(): Group = Group(
    id = id,
    name = name,
    description = description
)

/**
 * Converts a group to UI state for adding a new group.
 * @param isEntryValid `true` if the input is valid, `false` otherwise.
 * @return UI state for adding a new group.
 */
fun Group.toGroupUiState(isEntryValid: Boolean = false): NewGroupUiState = NewGroupUiState(
    groupDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/**
 * Converts a group to group details.
 * @return Group details.
 */
fun Group.toItemDetails(): GroupDetails = GroupDetails(
    id = id,
    name = name,
    description = description
)
