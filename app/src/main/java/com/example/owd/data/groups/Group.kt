package com.example.owd.data.groups

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a group entity in the database.
 *
 * @property id The unique identifier of the group.
 * @property name The name of the group.
 * @property description The description of the group.
 */
@Entity(tableName = "groups")
data class Group(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String
)
