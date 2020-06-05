package com.example.moneywise.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(vararg expense: Expense)

    @Query("SELECT * from expense_table where month=:month")
    fun getExpenseForMonth(month: Int): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table where month=:month and category=:category")
    fun getExpenseForCategory(category:String,month: Int): List<Expense>?

    @Query("DELETE FROM expense_table WHERE month=:month")
    fun clearExpenses(month: Int)

    @Query("SELECT SUM(amount) from expense_table where month=:month")
    fun getTotalMonthlyAmount(month: Int): Long?

    @Query("SELECT SUM(amount) FROM expense_table WHERE category=:category and month=:month")
    fun getCategoryTotalAmount(category: String,month: Int): Long?

    @Query("SELECT DISTINCT(category) from expense_table")
    fun getCategoryList():List<String>?
}