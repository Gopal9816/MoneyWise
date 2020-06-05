package com.example.moneywise.overview

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneywise.R
import com.example.moneywise.database.ExpenseDatabase
import com.example.moneywise.databinding.OverviewFragmentBinding


class OverviewFragment : Fragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = OverviewFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val dataSource = ExpenseDatabase.getInstance(application).expenseDatabaseDao

        val viewModelFactory = OverviewViewModelFactory(dataSource,application)

        val viewModel = ViewModelProviders.of(this,viewModelFactory).get(OverviewViewModel::class.java)

        binding.lifecycleOwner= this

        binding.viewModel = viewModel

        val listener = CategoryListener{
            viewModel.onNavigateToAddExpense()
        }

        val adapter = CategoryAdapter(listener)
        binding.categoryList.adapter = adapter

        viewModel.navigateToAddExpense.observe(viewLifecycleOwner, Observer {
            if (it == true){
                val categoryList = viewModel.categoryList.value?.toTypedArray()?: arrayOf<String>()
                val action = OverviewFragmentDirections.actionOverviewFragmentToAddExpenseFragment(categoryList)
                Log.i("OverviewFragment","Navigated to add expense")
                val navigationController = findNavController()
                navigationController.navigate(action)
                viewModel.onDoneNavigation()
            }
        })

        viewModel.categoryTotal.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }


}
