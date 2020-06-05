package com.example.moneywise.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moneywise.databinding.ListItemCategoryBinding

class CategoryAdapter(val listener: CategoryListener): ListAdapter<CategoryTotal,RecyclerView.ViewHolder>(CategoryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent,listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val data = getItem(position) as CategoryTotal
                holder.bind(data)
            }
        }


    }

    class ViewHolder private constructor(private val binding: ListItemCategoryBinding, val listener: CategoryListener): RecyclerView.ViewHolder(binding.root){
        fun bind(data: CategoryTotal){
            binding.categoryInfo = data
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup, listener: CategoryListener): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCategoryBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding,listener)
            }
        }
    }
}

class CategoryDiffCallback: DiffUtil.ItemCallback<CategoryTotal>(){
    override fun areItemsTheSame(oldItem: CategoryTotal, newItem: CategoryTotal): Boolean {
        return oldItem.category == newItem.category
    }

    override fun areContentsTheSame(oldItem: CategoryTotal, newItem: CategoryTotal): Boolean {
        return  oldItem == newItem
    }

}

class CategoryListener(val clickListener:() -> Unit){
    fun onClick() = clickListener()
}