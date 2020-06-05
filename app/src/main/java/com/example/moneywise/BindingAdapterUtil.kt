package com.example.moneywise

import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener


@BindingAdapter("valueAttrChanged")
fun AutoCompleteTextView.setListener(listener: InverseBindingListener?){
    this.onItemClickListener = if(listener != null){
        AdapterView.OnItemClickListener { _, _, position, _ ->
            listSelection = position
            listener.onChange()
        }
    } else {
        null
    }
}

@get:InverseBindingAdapter(attribute = "value")
@set:BindingAdapter("value")
var AutoCompleteTextView.selectedValue: String?
    get() {
        return if(listSelection != ListView.INVALID_POSITION){
            val item = adapter.getItem(listSelection).toString()
            Log.i("BindingUtils","$item is selected")
            item
        } else {
            Log.i("BindingUtil","I think this is the problem")
            null
        }
    }
    set(value) {
        val newValue = value?:adapter.getItem(0)
        setText(newValue.toString(),true)
        if (adapter is ArrayAdapter<*>){
            val position = (adapter as ArrayAdapter<Any?>).getPosition(newValue)
            listSelection = position
        }
    }

