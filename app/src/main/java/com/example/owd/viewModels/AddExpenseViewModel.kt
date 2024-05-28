package com.example.owd.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.owd.data.expenses.ExpensesRepository
import com.example.owd.data.groups.GroupsRepository
import com.example.owd.data.persons.Person
import com.example.owd.data.personsExpense.PersonExpenseRepository

class AddExpenseViewModel(
    savedStateHandle: SavedStateHandle,
    private val groupsRepository: GroupsRepository,
    private val expensesRepository: ExpensesRepository,
    private val personExpenseRepository: PersonExpenseRepository
): ViewModel(){
    var addExpenseUiState by mutableStateOf(AddExpenseUiState())
        private set

    fun updateUiState(expenseDetails: ExpenseDetails){
        addExpenseUiState = AddExpenseUiState(expenseDetails = expenseDetails, isEntryValid = validateInput(expenseDetails))
    }

    fun validateInput(expenseDetails: ExpenseDetails): Boolean {
        return true;
    }

    fun updateDescription(description: String) {
        addExpenseUiState = addExpenseUiState.copy(
            expenseDetails = addExpenseUiState.expenseDetails.copy(description = description)
        )
    }

    fun updateAmount(amount: String) {
        addExpenseUiState = addExpenseUiState.copy(
            expenseDetails = addExpenseUiState.expenseDetails.copy(amount = amount)
        )
    }

    fun updateTitle(title: String) {
        addExpenseUiState = addExpenseUiState.copy(
            expenseDetails = addExpenseUiState.expenseDetails.copy(name = title)
        )
    }
    //private val groupId: Int = checkNotNull(savedStateHandle[Expenses.groupId])

//    suspend fun saveExpense(expense: Expense){
//        expensesRepository.insert(expense)
////        for (personExpense in expense.personsExpense){
////            personExpenseRepository.insert(personExpense)
////        }
//    }
}

data class AddExpenseUiState(
    val expenseDetails: ExpenseDetails = ExpenseDetails(),
    val isEntryValid: Boolean = false
)

data class ExpenseDetails(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "",
    val description: String = "",
    val personsExpense: List<Person> = listOf()
)
