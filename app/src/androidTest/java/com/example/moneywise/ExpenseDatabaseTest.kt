package com.example.moneywise

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.moneywise.database.Expense
import com.example.moneywise.database.ExpenseDao
import com.example.moneywise.database.ExpenseDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class ExpenseDatabaseTest {

    private lateinit var database: ExpenseDatabase
    private lateinit var expenseDao: ExpenseDao

    @Before
    fun createDb(){
        var context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context,ExpenseDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        expenseDao = database.expenseDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun clearDb(){
        database.close()
    }

    @Test
    fun insertExpenses(){
        var expense = Expense(0L,"bill","Electricity",5000,5)
        expenseDao.insertExpense(expense)

        val results = expenseDao.getExpenseForCategory("Electricity",5)
        assertEquals(1,results?.size)
        println("The result is $results")

        println(Calendar.getInstance().get(Calendar.MONTH))
    }


}