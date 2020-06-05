package com.example.moneywise.overview

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.*
import com.example.moneywise.database.ExpenseDao
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.*
import java.util.*

class OverviewViewModel(
    private val database: ExpenseDao,
    application: Application) : AndroidViewModel(application) {

    // Total Expenses for current month
    private var _totalMonthlyExpense = MutableLiveData<Long>()
    val totalMonthlyExpense: LiveData<Long>
        get() = _totalMonthlyExpense

    // Job and Coroutine Scope for db operations
    private var databaseJob = Job()
    private var uiScope = CoroutineScope(databaseJob+Dispatchers.Main)

    // List of categories
    private var _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>>
        get() = _categoryList

    // List of total expenses under each category
    private val _categoryTotal = MutableLiveData<List<CategoryTotal>>()
    val categoryTotal: LiveData<List<CategoryTotal>>
        get() = _categoryTotal

    private val _navigateToAddExpense = MutableLiveData<Boolean>()
    val navigateToAddExpense: LiveData<Boolean>
        get() = _navigateToAddExpense

    init {
        uiScope.launch {
            val month = Calendar.getInstance().get(Calendar.MONTH)+1
            _totalMonthlyExpense.value = getMonthlyExpenseFromDatabase(month)
            _categoryList.value = getCategoryListFromDatabase()

            _categoryList.value?.let {
                val temp = mutableListOf<CategoryTotal>()
                for (item in it){
                    temp.add(getCategoryTotal(item,month))
                }
                _categoryTotal.value = temp
            }
        }
    }

    // Get the total for each category
    private suspend fun getCategoryTotal(category: String, month: Int): CategoryTotal {
        return withContext(Dispatchers.IO){
            val total = database.getCategoryTotalAmount(category, month)?:0
            CategoryTotal(category,total)
        }
    }

    // Get the list of categories
    private suspend fun getCategoryListFromDatabase(): List<String>? {
        return withContext(Dispatchers.IO){
            database.getCategoryList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        databaseJob.cancel()
    }

    // get monthly total
    private suspend fun getMonthlyExpenseFromDatabase(month: Int): Long? {
        return withContext(Dispatchers.IO){
            database.getTotalMonthlyAmount(month) ?: 0
        }
    }

    fun onNavigateToAddExpense(){
        _navigateToAddExpense.value = true
    }

    fun onDoneNavigation(){
        _navigateToAddExpense.value = false
    }

}

@Parcelize
data class CategoryTotal(
    val category: String,
    val total: Long
): Parcelable
