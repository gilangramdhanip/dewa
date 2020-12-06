package com.project.dewa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.dewa.R
import com.project.dewa.activities.DetailUmkmActivity
import com.project.dewa.model.ModelBarang
import kotlinx.android.synthetic.main.list_item_airterjun.view.*

class UmkmAdapter(private val destinationList: ArrayList<ModelBarang>): RecyclerView.Adapter<UmkmAdapter.ViewHolder>(), Filterable {
    private var filterListResult: ArrayList<ModelBarang> = destinationList

    fun setData(items: ArrayList<ModelBarang>) {

        filterListResult.clear()
        filterListResult.addAll(items)

        notifyDataSetChanged()
    }

    fun setFlter(items: ModelBarang){
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
        fun bind(barang: ModelBarang) {
            with(itemView){
                tvWisata.text = barang.name_barang
                if(barang.name_barang == ""){
                    imgWisata.setImageResource(R.drawable.default_img)
                }else{
                    Glide.with(context)
                        .load("http://siwita-lombok.monster/rest_api/rest-server-sig/assets/foto/"+barang.img_barang)
                        .apply(RequestOptions().override(500, 500))
                        .into(imgWisata)
                }


                itemView.setOnClickListener {
                    val intent = Intent(context, DetailUmkmActivity::class.java)
                    intent.putExtra(DetailUmkmActivity.EXTRA_DETAIl, barang)
                    context.startActivity(intent)
                }

            }
        }
    }

    override fun getFilter(): Filter {
        return object: android.widget.Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if(charSearch.isEmpty())
                    filterListResult = destinationList
                else {
                    val resultlist = ArrayList<ModelBarang>()
                    for (row in destinationList) {
                        if (row.id_kabupaten!!.toLowerCase().contains(charSearch.toLowerCase()) || row.id_kecamatan!!.toLowerCase().contains(charSearch.toLowerCase()))
                            resultlist.add(row)
                    }
                    filterListResult = resultlist
                }
                val filterResult = FilterResults()
                filterResult.values = filterListResult
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterListResult = results!!.values as ArrayList<ModelBarang>
                notifyDataSetChanged()
            }

        }
    }


}