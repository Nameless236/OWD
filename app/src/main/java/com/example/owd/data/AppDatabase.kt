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

@Database(entities = [Group::class, Person::class, PersonExpense::class, Expense::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
    abstract fun personDao(): PersonDao
    abstract fun expensesDao(): ExpensesDao
    abstract fun personExpenseDao(): PersonExpenseDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}