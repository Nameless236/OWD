package com.example.owd.data.personsExpense

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.owd.data.expenses.Expense
import com.example.owd.data.persons.Person

@Entity(tableName = "personExpense",
    foreignKeys = [ForeignKey(entity = Expense::class, parentColumns = ["id"], childColumns = ["expenseId"]),
        ForeignKey(entity = Person::class, parentColumns = ["id"], childColumns = ["personId"])],
    primaryKeys = ["personId", "expenseId"])
data class PersonExpense(
    val personId: Int,
    val expenseId: Int,
    val amount: Double
)