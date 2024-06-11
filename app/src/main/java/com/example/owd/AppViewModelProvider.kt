package com.example.owd

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.owd.viewModels.AddGroupViewModel
import com.example.owd.viewModels.GroupDetailsViewModel
import com.example.owd.viewModels.GroupsViewModel
/**
 * Provides ViewModel instances for the Owd application.
 */
object AppViewModelProvider {
    /**
     * Factory for creating ViewModels.
     */
    val Factory = viewModelFactory {
        /**
         * Initializes [GroupsViewModel].
         */
        initializer {
            GroupsViewModel(owdApplication().container.groupsRepository)
        }

        /**
         * Initializes [AddGroupViewModel].
         */
        initializer {
            AddGroupViewModel(
                groupsRepository = owdApplication().container.groupsRepository,
                personsRepository = owdApplication().container.personsRepository
            )
        }

        /**
         * Initializes [GroupDetailsViewModel].
         */
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

/**
 * Provides access to the Owd application instance from [CreationExtras].
 *
 * @receiver [CreationExtras] containing the Owd application instance.
 * @return Owd application instance.
 */
fun CreationExtras.owdApplication(): Owdapplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as Owdapplication)
