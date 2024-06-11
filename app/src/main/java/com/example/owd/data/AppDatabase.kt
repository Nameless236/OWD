package com.example.owd.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.owd.data.expenses.Expense
import com.example.owd.data.expenses.ExpensesDao
import com.example.owd.data.groups.Group
import com.example.owd.data.groups.GroupDao
import com.example.owd.data.persons.Person
import com.example.owd.data.persons.PersonDao
import com.example.owd.data.personsExpense.PersonExpense
import com.example.owd.data.personsExpense.PersonExpenseDao

/**
 * Database class responsible for managing the Room database instance.
 * It contains abstract methods to access DAOs for groups, persons, expenses, and person expenses.
 */
@Database(entities = [Group::class, Person::class, PersonExpense::class, Expense::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
    abstract fun personDao(): PersonDao
    abstract fun expensesDao(): ExpensesDao
    abstract fun personExpenseDao(): PersonExpenseDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        /**
         * Returns the database instance.
         * If the Instance is not null, returns it, otherwise creates a new database instance.
         *
         * @param context The application context.
         * @return The AppDatabase instance.
         */
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
