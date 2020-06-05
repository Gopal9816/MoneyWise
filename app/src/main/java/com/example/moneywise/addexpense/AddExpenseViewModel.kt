package com.example.moneywise.addexpense

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.*
import com.example.moneywise.database.Expense
import com.example.moneywise.database.ExpenseDao
import kotlinx.coroutines.*

class AddExpenseViewModel(
    val database: ExpenseDao,
    application: Application
) : AndroidViewModel(application) {

    val job = Job()
    val uiScope = CoroutineScope(job+Dispatchers.Main)

    private val _navigateToOverview = MutableLiveData<Boolean>()
    val navigateToOverview: LiveData<Boolean>
        get() = _navigateToOverview

    val name = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    val monthName = MutableLiveData<String>()
    val amount = MutableLiveData<String>()


    fun onSubmit(){
        uiScope.launch {
            val month = when(monthName.value){
                "January" -> 1
                "February" -> 2
                "March" -> 3
                "April" -> 4
                "May" -> 5
                "June" -> 6
                "July" -> 7
                "August" -> 8
                "September" -> 9
                "October" -> 10
                "November" -> 11
                else -> 12
            }
            val expense = Expense(
                name = name.value,
                category = category.value,
                month = month,
                amount = amount.value?.toLong()
            )
            insertNewExpense(expense)
            Log.i("AddExpenseViewModel", "${name.value},${category.value},${month},${amount.value}")
            _navigateToOverview.value = true
        }
    }

    private suspend fun insertNewExpense(expense: Expense) {
        return withContext(Dispatchers.IO){
            database.insertExpense(expense)
        }
    }

    fun onNavigationDone(){
        _navigateToOverview.value = false
    }


}
