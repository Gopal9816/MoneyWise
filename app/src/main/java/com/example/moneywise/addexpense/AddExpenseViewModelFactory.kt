package com.example.moneywise.addexpense

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneywise.database.ExpenseDao
import java.lang.IllegalArgumentException

class AddExpenseViewModelFactory(
    private val dataSource: ExpenseDao,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)){
            return AddExpenseViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Invalid ViewModel class")
    }
}