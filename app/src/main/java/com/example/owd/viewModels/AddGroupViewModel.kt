package com.example.owd.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupsRepository
import com.example.owd.data.persons.Person
import com.example.owd.data.persons.PersonsRepository

class AddGroupViewModel(
    private val groupsRepository: GroupsRepository,
    private val personsRepository: PersonsRepository) : ViewModel()
{
    var groupUIState by mutableStateOf(NewGroupUiState())
        private set

    var newMemberName by mutableStateOf("")
    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    suspend fun saveItem() {
        if (validateInput(groupUIState.groupDetails)) {
            val id = groupsRepository.insert(groupUIState.groupDetails.toItem())
            for (member in groupUIState.groupDetails.members) {
                personsRepository.insert(Person(name = member, groupId = id))
            }
        }
    }

    fun updateUiState(groupDetails: GroupDetails) {
        groupUIState =
            NewGroupUiState(groupDetails = groupDetails, isEntryValid = validateInput(groupDetails))
    }

    private fun validateInput(uiState: GroupDetails = groupUIState.groupDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && members.isNotEmpty()
        }
    }


    fun addMember() {
        groupUIState = groupUIState.copy(
            groupDetails = groupUIState.groupDetails.copy(
                members = groupUIState.groupDetails.members + newMemberName
            )
        )
        newMemberName = ""
    }

    fun updateDescription(description: String) {
        groupUIState = groupUIState.copy(
            groupDetails = groupUIState.groupDetails.copy(description = description)
        )
    }

    fun updateName(name: String) {
        groupUIState = groupUIState.copy(
            groupDetails = groupUIState.groupDetails.copy(name = name)
        )
    }
}

data class NewGroupUiState(
    val groupDetails: GroupDetails = GroupDetails(),
    val isEntryValid: Boolean = false
)

data class GroupDetails(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val members: List<String> = listOf()
)

fun GroupDetails.toItem(): Group = Group (
    id = id,
    name = name,
    description = description
)

fun Group.toGroupUiState(isEntryValid: Boolean = false): NewGroupUiState = NewGroupUiState(
    groupDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Group.toItemDetails(): GroupDetails = GroupDetails(
    id = id,
    name = name,
    description = description
)