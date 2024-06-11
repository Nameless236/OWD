package com.example.owd.data.personsExpense

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.owd.data.expenses.Expense
import com.example.owd.data.persons.Person

/**
 * Represents a relationship between a person and an expense.
 * This entity is used to track the amount of an expense paid by a specific person.
 *
 * @property personId The ID of the person.
 * @property expenseId The ID of the expense.
 * @property amount The amount of the expense paid by the person.
 */
@Entity(
    tableName = "personExpense",
    foreignKeys = [
        ForeignKey(
            entity = Expense::class,
            parentColumns = ["id"],
            childColumns = ["expenseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["personId", "expenseId"]
)
data class PersonExpense(
    val personId: Long,
    val expenseId: Long,
    val amount: Double
)
