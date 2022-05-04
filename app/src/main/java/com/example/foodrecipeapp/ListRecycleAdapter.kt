package com.example.foodrecipeapp

import android.view.LayoutInflater
import androidx.appcompat.view.menu.ActionMenuItemView
import  android.view.View
import androidx.navigation.ui.navigateUp
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycle_row.view.*

class ListRecycleAdapter(val foodlist:ArrayList<String>, val idlist:ArrayList<Int>):RecyclerView.Adapter<ListRecycleAdapter.FoodHolder>() {

    class FoodHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
      val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycle_row,parent,false)
    return FoodHolder(view)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
     holder.itemView.recyle_row_text.text=foodlist[position]
        holder.itemView.setOnClickListener {
            val action =ListFragmentDirections.actionListFragmentToRecipeFragment("")
        }
    }

    override fun getItemCount(): Int {
        return foodlist.size
    }
}