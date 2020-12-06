package com.project.dewa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.dewa.R
import com.project.dewa.model.ModelMain
import kotlinx.android.synthetic.main.item_grid.view.*


class MainAdapter(private val items: ArrayList<ModelMain>, private val onSelectDatax : onSelectData): RecyclerView.Adapter<MainAdapter.ViewHolder>(){


    interface onSelectData {
        fun onSelected(mdlMain: ModelMain?)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    //Class Holder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(destination: ModelMain) {
            with(itemView){

                tvMainData.text = destination.txtName
                imgMainData.setImageResource(destination.imageSource)

                itemView.setOnClickListener {
                    onSelectDatax!!.onSelected(destination)
                }

            }
        }
    }



}