package com.example.owd.data.persons

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.owd.data.groups.Group

/**
 * Entity class representing a person.
 *
 * @property id The unique identifier of the person.
 * @property name The name of the person.
 * @property groupId The ID of the group to which the person belongs.
 */
@Entity(tableName = "persons",
    foreignKeys = [ForeignKey(
        entity = Group::class,
        parentColumns = ["id"],
        childColumns = ["groupId"],
        onDelete = ForeignKey.CASCADE)])
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val groupId: Long,
)
