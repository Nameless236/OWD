package com.example.owd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "personExpense",
    foreignKeys = [ForeignKey(entity = Expense::class, parentColumns = ["id"], childColumns = ["expenseId"]),
        ForeignKey(entity = Person::class, parentColumns = ["id"], childColumns = ["personId"])])
data class PersonExpense(
    @PrimaryKey(autoGenerate = false)
    val personId: Int,
    @PrimaryKey(autoGenerate = false)
    val expenseId: Int
)