package com.example.owd.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "persons", foreignKeys = [ForeignKey(entity = Group::class, parentColumns = ["id"], childColumns = ["groupId"])])
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val groupId: Int
)