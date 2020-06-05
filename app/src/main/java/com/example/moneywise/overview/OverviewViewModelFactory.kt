package com.example.moneywise.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneywise.database.ExpenseDao
import java.lang.IllegalArgumentException

class OverviewViewModelFactory(
    private val dataSource: ExpenseDao,
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OverviewViewModel::class.java)){
            return OverviewViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}