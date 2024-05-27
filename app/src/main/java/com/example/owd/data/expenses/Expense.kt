package com.example.owd.data.expenses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.owd.data.groups.Group
import com.example.owd.data.persons.Person

@Entity(tableName = "expenses",
    foreignKeys = [ForeignKey(entity = Group::class, parentColumns = ["id"], childColumns = ["groupId"], onDelete = ForeignKey.CASCADE), ForeignKey(entity = Person::class, parentColumns = ["id"], childColumns = ["paidBy"], onDelete = ForeignKey.CASCADE)])
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val groupId: Long,
    val paidBy: Long
)