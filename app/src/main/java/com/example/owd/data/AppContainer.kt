package com.example.owd.data

import android.content.Context
import com.example.owd.data.expenses.ExpensesOfflineRepository
import com.example.owd.data.expenses.ExpensesRepository
import com.example.owd.data.groups.GroupsOfflineRepository
import com.example.owd.data.groups.GroupsRepository
import com.example.owd.data.persons.PersonsOfflineRepository
import com.example.owd.data.persons.PersonsRepository
import com.example.owd.data.personsExpense.PersonExpenseOfflineRepository
import com.example.owd.data.personsExpense.PersonExpenseRepository

interface AppContainer {
    val groupsRepository: GroupsRepository
    val personsRepository: PersonsRepository
    val expensesRepository: ExpensesRepository
    val personExpenseRepository: PersonExpenseRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val groupsRepository: GroupsRepository = GroupsOfflineRepository(AppDatabase.getDatabase(context).groupDao())
    override val personsRepository: PersonsRepository = PersonsOfflineRepository(AppDatabase.getDatabase(context).personDao())
    override val expensesRepository: ExpensesRepository = ExpensesOfflineRepository(AppDatabase.getDatabase(context).expensesDao())
    override val personExpenseRepository: PersonExpenseRepository = PersonExpenseOfflineRepository(AppDatabase.getDatabase(context).personExpenseDao())
}