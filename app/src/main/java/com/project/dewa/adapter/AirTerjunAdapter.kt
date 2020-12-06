package com.project.dewa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.dewa.R
import com.project.dewa.activities.DetailActivity
import com.project.dewa.model.ModelDestination
import kotlinx.android.synthetic.main.list_item_airterjun.view.*

class AirTerjunAdapter(private val destinationList: ArrayList<ModelDestination>): RecyclerView.Adapter<AirTerjunAdapter.ViewHolder>(),
    Filterable {

    internal var filterListResult: ArrayList<ModelDestination>

    init{
        this.filterListResult = destinationList
    }

    fun setData(items: ArrayList<ModelDestination>) {

        filterListResult.clear()
        filterListResult.addAll(items)
        filterListResult.sortWith(Comparator { o1, o2 ->

            return@Comparator o1.name_destination.compareTo(o2.name_destination)
        })

        notifyDataSetChanged()
    }

    fun setFlter(items: ModelDestination){
        filterListResult.clear()
        filterListResult.addAll(listOf(items))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_airterjun, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return destinationList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(destinationList[position])
    }

    //Class Holder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(destination: ModelDestination) {
            with(itemView){

                tvWisata.text = destination.name_destination
                if(destination.img_destination == ""){
                    imgWisata.setImageResource(R.drawable.default_img)
                }else{
                    Glide.with(context)
                        .load("http://siwita-lombok.monster/rest_api/rest-server-sig/assets/foto/"+destination.img_destination)
                        .into(imgWisata)
                }

                itemView.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DETAIl, destination)
                    context.startActivity(intent)
                }

            }
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if(charSearch.isEmpty())
                    filterListResult = destinationList
                else {
                    val resultlist = ArrayList<ModelDestination>()
                    for (row in destinationList) {
                        if (row.id_kabupaten!!.toLowerCase().contains(charSearch.toLowerCase()) || row.id_kecamatan!!.toLowerCase().contains(charSearch.toLowerCase()))
                            resultlist.add(row)
                    }
                    filterListResult = resultlist
                }
                val filterResult = Filter.FilterResults()
                filterResult.values = filterListResult
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterListResult = results!!.values as ArrayList<ModelDestination>
                notifyDataSetChanged()
            }

        }
    }


}