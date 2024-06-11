package com.example.owd.data.expenses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.owd.data.groups.Group
import com.example.owd.data.persons.Person

/**
 * Data class representing an expense entity.
 * An expense represents a financial transaction within a group.
 *
 * @property id The unique identifier of the expense. Automatically generated if not provided.
 * @property name The name or description of the expense.
 * @property amount The amount of the expense.
 * @property groupId The ID of the group to which the expense belongs. References the [Group] entity.
 * @property paidBy The ID of the person who paid for the expense. References the [Person] entity.
 */
@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(entity = Group::class, parentColumns = ["id"], childColumns = ["groupId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Person::class, parentColumns = ["id"], childColumns = ["paidBy"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val groupId: Long,
    val paidBy: Long
)
