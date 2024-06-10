package com.example.owd.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owd.data.expenses.Expense
import com.example.owd.data.expenses.ExpensesRepository
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupsRepository
import com.example.owd.data.persons.Person
import com.example.owd.data.persons.PersonsRepository
import com.example.owd.data.personsExpense.PersonExpenseRepository
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
    private val personExpenseRepository: PersonExpenseRepository,
    private val personsRepository: PersonsRepository
) : ViewModel() {

    private var groupId: Long = 0

    private var groupFlow = groupsRepository.getGroup(groupId.toInt())
        .filterNotNull()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = Group(0, "", "")
        )

    private var expensesFlow = expenseRepository.getAllExpenses(groupId.toInt())
        .filterNotNull()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    private var personsFlow = personsRepository.getPersonsGroup(groupId.toInt())
        .filterNotNull()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    var _addExpenseUiState by mutableStateOf(AddExpenseUiState())
        private set

    var uiState: StateFlow<GroupDetailUiState> = combine(
        groupFlow,
        expensesFlow,
        personsFlow
    ) { group, expenses, persons ->
        GroupDetailUiState(
            group = group,
            expensesList = expenses,
            members = persons
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = GroupDetailUiState()
    )

    fun setGroupId(groupId: Long) {
        this.groupId = groupId
        // Load necessary data with groupId
        groupFlow = groupsRepository.getGroup(groupId.toInt())
            .filterNotNull()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = Group(0, "", "")
            )

        expensesFlow = expenseRepository.getAllExpenses(groupId.toInt())
            .filterNotNull()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )

        personsFlow = personsRepository.getPersonsGroup(groupId.toInt())
            .filterNotNull()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )

        uiState = combine(
            groupFlow,
            expensesFlow,
            personsFlow
        ) { group, expenses, persons ->
            GroupDetailUiState(
                group = group,
                expensesList = expenses,
                members = persons
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GroupDetailUiState()
        ).also { uiState = it }



        _addExpenseUiState = _addExpenseUiState.copy(
            expenseDetails = ExpenseDetails(personsExpense = personsFlow.value, groupId = groupId.toInt())
        )
    }
    fun updateUiState(expenseDetails: ExpenseDetails){
        _addExpenseUiState = _addExpenseUiState.copy(expenseDetails = expenseDetails)
    }

    private fun validateInput(expenseDetails: ExpenseDetails): Boolean {
        //return expenseDetails.amount.isNotBlank() && expenseDetails.name.isNotBlank() && expenseDetails.personsExpense.isNotEmpty();
        return true
    }

    fun updateDescription(desc: String) {
        _addExpenseUiState = _addExpenseUiState.copy(
            expenseDetails = _addExpenseUiState.expenseDetails.copy(description = desc)
        )
    }

    fun updateAmount(amount: String) {
        _addExpenseUiState = _addExpenseUiState.copy(
            expenseDetails = _addExpenseUiState.expenseDetails.copy(amount = amount)
        )
    }

    fun updateTitle(title: String) {
        _addExpenseUiState = _addExpenseUiState.copy(
            expenseDetails = _addExpenseUiState.expenseDetails.copy(name = title)
        )
    }

    suspend fun saveExpense() {
        if (validateInput(_addExpenseUiState.expenseDetails)) {
           val expenseId = expenseRepository.insert(_addExpenseUiState.expenseDetails.toExpense())
        }
    }

    fun updateSelectedMembers(selectedMembers: List<Person>) {
        _addExpenseUiState = _addExpenseUiState.copy(
            expenseDetails = _addExpenseUiState.expenseDetails.copy(personsExpense = selectedMembers)
        )
    }

    fun updatePayer(payer: Person) {
        _addExpenseUiState = _addExpenseUiState.copy(
            expenseDetails = _addExpenseUiState.expenseDetails.copy(paidBy = payer.id.toInt())
        )
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GroupDetailUiState(
    val group: Group = Group(0, "", ""),
    val expensesList: List<Expense> = emptyList(),
    val members: List<Person> = emptyList()
)


data class AddExpenseUiState(
    val expenseDetails: ExpenseDetails = ExpenseDetails(),
    val isEntryValid: Boolean = false
)

data class ExpenseDetails(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "",
    val description: String = "",
    val personsExpense: List<Person> = emptyList(),
    val groupId: Int = 0,
    val paidBy: Int = 0
)

fun ExpenseDetails.toExpense(): Expense = Expense(
    id = id.toLong(),
    name = name,
    amount = amount.toDoubleOrNull() ?: 0.0,
    groupId = groupId.toLong(),
    paidBy = paidBy.toLong(),
)