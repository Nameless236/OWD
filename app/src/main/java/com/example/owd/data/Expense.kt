package com.example.owd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "expenses",
    foreignKeys = [ForeignKey(entity = Group::class, parentColumns = ["id"], childColumns = ["groupId"])])
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val amount: Double,
    val groupId: Int
)