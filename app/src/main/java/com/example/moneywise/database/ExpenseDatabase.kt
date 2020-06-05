package com.example.moneywise.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Expense::class],version = 1, exportSchema = false)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract val expenseDatabaseDao: ExpenseDao

    companion object{

        @Volatile
        private var INSTANCE: ExpenseDatabase? = null


        fun getInstance(context: Context): ExpenseDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        ExpenseDatabase::class.java,
                        "expense_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}