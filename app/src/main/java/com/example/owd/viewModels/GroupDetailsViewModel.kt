package com.example.owd.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owd.data.expenses.Expense
import com.example.owd.data.expenses.ExpensesRepository
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupsRepository
import com.example.owd.data.persons.Person
import com.example.owd.data.persons.PersonsRepository
import com.example.owd.data.personsExpense.PersonExpense
import com.example.owd.data.personsExpense.PersonExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GroupDetailsViewModel(
    private val groupsRepository: GroupsRepository,
    private val expenseRepository: ExpensesRepository,
    private val personExpenseRepository: PersonExpenseRepository,
    private val personsRepository: PersonsRepository
) : ViewModel() {

    private var groupId: Long = 0
    var displayBalances = false

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
        updateFlows()
    }

    private fun updateFlows() {
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
                members = persons,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GroupDetailUiState()
        ).also { uiState = it }

        _addExpenseUiState = _addExpenseUiState.copy(
            expenseDetails = ExpenseDetails(personsExpense = uiState.value.members, groupId = groupId.toInt())
        )

        updateAmounts()
    }

    fun updateUiState(expenseDetails: ExpenseDetails){
        _addExpenseUiState = _addExpenseUiState.copy(expenseDetails = expenseDetails)
    }

    private fun validateInput(expenseDetails: ExpenseDetails): Boolean {
        return expenseDetails.amount.toDoubleOrNull() != null && expenseDetails.name != "" && expenseDetails.personsExpense.isNotEmpty() && expenseDetails.paidBy != 0
        //return true
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
            _addExpenseUiState.expenseDetails.personsExpense.forEach {
                var amount = _addExpenseUiState.expenseDetails.amount.toDouble() / _addExpenseUiState.expenseDetails.personsExpense.size
                if (it.id.toInt() != _addExpenseUiState.expenseDetails.paidBy)
                    amount = -amount
                personExpenseRepository.insert(PersonExpense(personId = it.id, expenseId = expenseId, amount))
            }
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

    suspend fun deleteGroup() {
        groupsRepository.delete(uiState.value.group)
    }

    fun updateAmounts() {
        viewModelScope.launch {
            val amounts = getAmountsForMembers(uiState.value.members)
            _addExpenseUiState = _addExpenseUiState.copy(amounts = amounts)
        }
    }

    private suspend fun getAmountsForMembers(members: List<Person>): List<Pair<Person, Float>> {
        val amounts = mutableListOf<Pair<Person, Float>>()
        for (member in members) {
            val paidAmount = personExpenseRepository.getAmmountForPerson(member.id.toInt()).firstOrNull() ?: 0.0f
            amounts.add(member to paidAmount)
        }
        return amounts
    }

    fun updateDisplayBalances(display: Boolean = true) {
        displayBalances = display
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GroupDetailUiState(
    val group: Group = Group(0, "", ""),
    val expensesList: List<Expense> = emptyList(),
    val members: List<Person> = emptyList(),
)

data class AddExpenseUiState(
    val expenseDetails: ExpenseDetails = ExpenseDetails(),
    val amounts: List<Pair<Person, Float>> = emptyList(),
    val isEntryValid: Boolean = false
)

data class ExpenseDetails(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "",
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
