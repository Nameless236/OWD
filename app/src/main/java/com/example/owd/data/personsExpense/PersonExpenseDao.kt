package com.example.owd.data.personsExpense

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.owd.data.persons.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonExpenseDao {
    @Insert
    suspend fun insert(personExpense: PersonExpense)

    @Update
    suspend fun update(personExpense: PersonExpense)

    @Delete
    suspend fun delete(personExpense: PersonExpense)

    @Query("SELECT * from persons WHERE id in (SELECT personId from personExpense WHERE expenseId = :expenseId)")
    fun getPersons(expenseId: Int): Flow<List<Person?>>

    @Query("SELECT count(amount) from personExpense WHERE personId = :pId")
    fun getAmmountForPerson(pId: Int): Flow<Float>
}
