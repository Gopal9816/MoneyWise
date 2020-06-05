package com.example.moneywise.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0L,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "category")
    var category: String?,
    @ColumnInfo(name = "amount")
    var amount: Long?,
    @ColumnInfo(name = "month")
    var month: Int?
)