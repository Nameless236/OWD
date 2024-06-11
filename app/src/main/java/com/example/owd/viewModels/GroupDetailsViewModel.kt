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

/**
 * ViewModel responsible for managing group details.
 *
 * @param groupsRepository Repository for managing groups.
 * @param expenseRepository Repository for managing expenses.
 * @param personExpenseRepository Repository for managing person expenses.
 * @param personsRepository Repository for managing persons.
 */
class GroupDetailsViewModel(
    private val groupsRepository: GroupsRepository,
    private val expenseRepository: ExpensesRepository,
    private val personExpenseRepository: PersonExpenseRepository,
    private val personsRepository: PersonsRepository
) : ViewModel() {

    private var groupId: Long = 0

    // Flow representing the group details
    private var groupFlow = groupsRepository.getGroup(groupId.toInt())
        .filterNotNull()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Group(0, "", "")
        )

    // Flow representing the list of expenses
    private var expensesFlow = expenseRepository.getAllExpenses(groupId.toInt())
        .filterNotNull()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Flow representing the list of persons in the group
    private var personsFlow = personsRepository.getPersonsGroup(groupId.toInt())
        .filterNotNull()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // UI state representing the state of adding an expense
    var addExpenseUiState by mutableStateOf(AddExpenseUiState())
        private set

    // UI state representing whether to display balances chart or not
    var displayBalancesChart by mutableStateOf(DetailUiState())
        private set

    // Combined UI state representing group details, expenses list, and members
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
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GroupDetailUiState()
    )

    /**
     * Sets the group ID.
     *
     * @param groupId ID of the group.
     */
    fun setGroupId(groupId: Long) {
        this.groupId = groupId
        updateFlows()
    }

    /**
     * Updates the flows based on the group ID.
     */
    private fun updateFlows() {
        groupFlow = groupsRepository.getGroup(groupId.toInt())
            .filterNotNull()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Group(0, "", "")
            )

        expensesFlow = expenseRepository.getAllExpenses(groupId.toInt())
            .filterNotNull()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

        personsFlow = personsRepository.getPersonsGroup(groupId.toInt())
            .filterNotNull()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
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
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GroupDetailUiState()
        ).also { uiState = it }

        addExpenseUiState = addExpenseUiState.copy(
            expenseDetails = ExpenseDetails(
                personsExpense = uiState.value.members,
                groupId = groupId.toInt()
            )
        )

        updateAmounts()
    }

    /**
     * Updates the UI state for adding an expense.
     *
     * @param expenseDetails Details of the expense.
     */
    fun updateUiState(expenseDetails: ExpenseDetails) {
        addExpenseUiState = addExpenseUiState.copy(expenseDetails = expenseDetails)
    }

    /**
     * Validates the input for adding an expense.
     *
     * @param expenseDetails Details of the expense.
     * @return true if input is valid, false otherwise.
     */
    private fun validateInput(expenseDetails: ExpenseDetails): Boolean {
        return expenseDetails.amount.toDoubleOrNull() != null &&
                expenseDetails.name != "" &&
                expenseDetails.personsExpense.isNotEmpty() &&
                expenseDetails.paidBy != 0
    }

    /**
     * Updates the amount for the expense.
     *
     * @param amount Amount of the expense.
     */
    fun updateAmount(amount: String) {
        addExpenseUiState = addExpenseUiState.copy(
            expenseDetails = addExpenseUiState.expenseDetails.copy(amount = amount)
        )
    }

    /**
     * Updates the title of the expense.
     *
     * @param title Title of the expense.
     */
    fun updateTitle(title: String) {
        addExpenseUiState = addExpenseUiState.copy(
            expenseDetails = addExpenseUiState.expenseDetails.copy(name = title)
        )
    }

    /**
     * Saves the expense if the input is valid.
     *
     * @throws IllegalStateException if the input is not valid.
     */
    suspend fun saveExpense() {
        if (validateInput(addExpenseUiState.expenseDetails)) {
            val expenseId = expenseRepository.insert(addExpenseUiState.expenseDetails.toExpense())
            addExpenseUiState.expenseDetails.personsExpense.forEach {
                var amount =
                    addExpenseUiState.expenseDetails.amount.toDouble() / addExpenseUiState.expenseDetails.personsExpense.size
                if (it.id.toInt() != addExpenseUiState.expenseDetails.paidBy)
                    amount = -amount
                personExpenseRepository.insert(
                    PersonExpense(
                        personId = it.id,
                        expenseId = expenseId,
                        amount
                    )
                )
            }
        } else {
            throw IllegalStateException("Expense input is not valid.")
        }
    }

    /**
     * Updates the selected members for the expense.
     *
     * @param selectedMembers List of selected members.
     */
    fun updateSelectedMembers(selectedMembers: List<Person>) {
        addExpenseUiState = addExpenseUiState.copy(
            expenseDetails = addExpenseUiState.expenseDetails.copy(personsExpense = selectedMembers)
        )
    }

    /**
     * Updates the payer for the expense.
     *
     * @param payer The payer.
     */
    fun updatePayer(payer: Person) {
        addExpenseUiState = addExpenseUiState.copy(
            expenseDetails = addExpenseUiState.expenseDetails.copy(paidBy = payer.id.toInt())
        )
    }

    /**
     * Deletes the group.
     */
    suspend fun deleteGroup() {
        groupsRepository.delete(uiState.value.group)
    }

    /**
     * Updates the amounts for the members.
     */
    fun updateAmounts() {
        viewModelScope.launch {
            val amounts = getAmountsForMembers(uiState.value.members)
            addExpenseUiState = addExpenseUiState.copy(amounts = amounts)
        }
    }

    /**
     * Retrieves the amounts for the members.
     *
     * @param members List of members.
     * @return List of pairs containing each member and their respective amount.
     */
    private suspend fun getAmountsForMembers(members: List<Person>): List<Pair<Person, Float>> {
        val amounts = mutableListOf<Pair<Person, Float>>()
        for (member in members) {
            val paidAmount =
                personExpenseRepository.getAmmountForPerson(member.id.toInt()).firstOrNull() ?: 0.0f
            amounts.add(member to paidAmount)
        }
        return amounts
    }

    /**
     * Updates the display of balances chart.
     *
     * @param display Boolean flag indicating whether to display the balances chart.
     */
    fun updateDisplayBalances(display: Boolean = true) {
        displayBalancesChart = displayBalancesChart.copy(display = display)
    }

    /**
     * Deletes the expense.
     *
     * @param expense The expense to delete.
     */
    suspend fun deleteExpense(expense: Expense) {
        expenseRepository.delete(expense)
    }
}

    /**
     * UI state representing the details of a group.
     *
     * @property group The group details.
     * @property expensesList List of expenses.
     * @property members List of group members.
     */
    data class GroupDetailUiState(
        val group: Group = Group(0, "", ""),
        val expensesList: List<Expense> = emptyList(),
        val members: List<Person> = emptyList()
    )

    /**
     * UI state representing the details of adding an expense.
     *
     * @property expenseDetails Details of the expense.
     * @property amounts List of pairs containing each member and their respective amount.
     * @property isEntryValid Flag indicating whether the expense entry is valid.
     */
    data class AddExpenseUiState(
        val expenseDetails: ExpenseDetails = ExpenseDetails(),
        val amounts: List<Pair<Person, Float>> = emptyList(),
        val isEntryValid: Boolean = false
    )

    /**
     * UI state representing whether to display balances chart or not.
     *
     * @property display Boolean flag indicating whether to display the balances chart.
     */
    data class DetailUiState(
        val display: Boolean = false
    )

    /**
     * Represents the details of an expense.
     *
     * @property id The ID of the expense.
     * @property name The name of the expense.
     * @property amount The amount of the expense.
     * @property personsExpense List of persons involved in the expense.
     * @property groupId The ID of the group to which the expense belongs.
     * @property paidBy The ID of the person who paid the expense.
     */
    data class ExpenseDetails(
        val id: Int = 0,
        val name: String = "",
        val amount: String = "",
        val personsExpense: List<Person> = emptyList(),
        val groupId: Int = 0,
        val paidBy: Int = 0
    )

    /**
     * Converts [ExpenseDetails] to [Expense].
     *
     * @return The converted [Expense].
     */
    fun ExpenseDetails.toExpense(): Expense = Expense(
        id = id.toLong(),
        name = name,
        amount = amount.toDoubleOrNull() ?: 0.0,
        groupId = groupId.toLong(),
        paidBy = paidBy.toLong()
    )
