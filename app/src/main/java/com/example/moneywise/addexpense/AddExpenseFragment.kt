
package com.example.moneywise.addexpense

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.database.DataSetObserver
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.moneywise.R
import com.example.moneywise.database.ExpenseDatabase
import com.example.moneywise.databinding.AddExpenseFragmentBinding


class AddExpenseFragment : Fragment() {

    companion object {
        fun newInstance() = AddExpenseFragment()
    }

    private lateinit var viewModel: AddExpenseViewModel

    private lateinit var binding: AddExpenseFragmentBinding

    private lateinit var categoryList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddExpenseFragmentBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val database = ExpenseDatabase.getInstance(application)
        categoryList = AddExpenseFragmentArgs.fromBundle(requireArguments()).categoryList.toMutableList()
        val factory = AddExpenseViewModelFactory(database.expenseDatabaseDao,application)
        viewModel = ViewModelProviders.of(this,factory).get(AddExpenseViewModel::class.java)

        val arrayAdapter = ArrayAdapter(application,R.layout.dropdown_menu_item,categoryList)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        

        binding.editCategoryDropdown.setAdapter(arrayAdapter)
        binding.editCategoryDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.selectedItem as String
                Log.i("AddExpense","I was clicked to $item")
            }

        }

        val monthArrayAdapter = ArrayAdapter.createFromResource(application,R.array.month_names,R.layout.dropdown_menu_item)
        binding.editMonthDropdown.setAdapter(monthArrayAdapter)

        viewModel.navigateToOverview.observe(viewLifecycleOwner, Observer {
            if (it == true){
                findNavController().navigate(R.id.action_addExpenseFragment_to_overviewFragment)
                Log.i("AddExpenseFragment","Navigated to overview")
                viewModel.onNavigationDone()
            }
        })



        return binding.root
    }

}
