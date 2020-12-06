package com.project.dewa.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.dewa.BuildConfig
import com.project.dewa.R
import com.project.dewa.adapter.UmkmAdapter
import com.project.dewa.model.ModelBarang
import com.project.dewa.viewmodel.MainViewModelUmkm
import kotlinx.android.synthetic.main.activity_umkm.*
import kotlinx.android.synthetic.main.activity_umkm.btn_cari
import kotlinx.android.synthetic.main.activity_umkm.btn_clear
import kotlinx.android.synthetic.main.activity_umkm.destiny_recycler_view
import kotlinx.android.synthetic.main.activity_umkm.not_found
import kotlinx.android.synthetic.main.activity_umkm.progressbar
import kotlinx.android.synthetic.main.activity_umkm.search_view
import kotlinx.android.synthetic.main.activity_umkm.toolbar_wisata
import kotlinx.android.synthetic.main.activity_umkm.txv_jumlah_destinasi

class UmkmActivity : AppCompatActivity() {
    private val barang = ArrayList<ModelBarang>()

    lateinit var destinationAdapter : UmkmAdapter
    lateinit var mainViewModel : MainViewModelUmkm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_umkm)
        toolbar_wisata.title = "Daftar Barang dari Lombok"
        setSupportActionBar(toolbar_wisata)
        if (BuildConfig.DEBUG && supportActionBar == null) {
            error("Assertion failed")
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        loadDestinationList()
    }

    private fun loadDestinationList(){
        destinationAdapter = UmkmAdapter(barang)
        destinationAdapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModelUmkm::class.java
        )

        showLoading(true)

        mainViewModel.setBarang()
        destiny_recycler_view.setHasFixedSize(true)
        destiny_recycler_view.layoutManager = GridLayoutManager(
            this,
            2,
            RecyclerView.VERTICAL,
            false
        )
        destiny_recycler_view.adapter = destinationAdapter

        mainViewModel.getBarang().observe(this, Observer { destination ->
            if (destination != null) {
                destinationAdapter.setData(destination)
                txv_jumlah_destinasi.text = destinationAdapter.itemCount.toString()
                not_found.visibility= View.GONE

                btn_cari.setOnClickListener {
                    search_view.visibility = View.VISIBLE
                    btn_cari.visibility = View.GONE

                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        destination
                    )
                    search_view.threshold = 0
                    search_view.setAdapter(adapter)
                    search_view.setOnItemClickListener { adapterView, view, i, l ->
                        val a = search_view.adapter.getItem(i) as ModelBarang
                        destinationAdapter.setFlter(a)
                        txv_jumlah_destinasi.text = destinationAdapter.itemCount.toString()

                        if (search_view.text.equals("")) {
                            btn_clear.visibility = View.GONE
                        } else {
                            btn_clear.visibility = View.VISIBLE
                            btn_clear.setOnClickListener {
                                search_view.text.clear()
                                btn_cari.visibility = View.VISIBLE
                                search_view.visibility = View.GONE
                                btn_clear.visibility = View.GONE
                            }
                        }
                    }
                }
                showLoading(false)
            }else{
                not_found.visibility = View.VISIBLE
                destiny_recycler_view.visibility = View.GONE
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean){
        if(state){
            progressbar.visibility= View.VISIBLE
            progress_title.visibility = View.VISIBLE
        }
        else{
            progressbar.visibility= View.GONE
            progress_title.visibility = View.GONE
        }
    }
}