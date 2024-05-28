package com.example.owd.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owd.data.expenses.Expense
import com.example.owd.data.expenses.ExpensesRepository
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupsRepository
import com.example.owd.data.personsExpense.PersonExpenseRepository
import com.example.owd.screens.Expenses
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GroupDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val groupsRepository: GroupsRepository,
    private val expenseRepository: ExpensesRepository,
    private val personExpenseRepository: PersonExpenseRepository
) : ViewModel() {

    private val groupId: Int = checkNotNull(savedStateHandle[Expenses.groupId])

    private val groupFlow = groupsRepository.getGroup(groupId)
        .filterNotNull()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = Group(0, "", "")
        )

    private val expensesFlow = expenseRepository.getAllExpenses(groupId)
        .filterNotNull()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    val uiState: StateFlow<GroupDetailUiState> = combine(
        groupFlow,
        expensesFlow
    ) { group, expenses ->
        GroupDetailUiState(
            group = group,
            expensesList = expenses
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = GroupDetailUiState()
    )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GroupDetailUiState(
    val group: Group = Group(0, "", ""),
    val expensesList: List<Expense> = emptyList()
)
