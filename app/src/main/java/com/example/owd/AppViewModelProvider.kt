package com.example.owd

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.owd.viewModels.AddGroupViewModel
import com.example.owd.viewModels.GroupDetailsViewModel
import com.example.owd.viewModels.GroupsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            GroupsViewModel(owdApplication().container.groupsRepository)
        }

        initializer {
            AddGroupViewModel(owdApplication().container.groupsRepository, owdApplication().container.personsRepository)
        }

        initializer {
            GroupDetailsViewModel(
                groupsRepository = owdApplication().container.groupsRepository,
                expenseRepository = owdApplication().container.expensesRepository,
                personExpenseRepository = owdApplication().container.personExpenseRepository,
                personsRepository = owdApplication().container.personsRepository
            )
        }
    }
}

fun CreationExtras.owdApplication(): Owdapplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as Owdapplication)
