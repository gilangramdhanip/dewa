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
import com.project.dewa.adapter.AirTerjunAdapter
import com.project.dewa.model.ModelDestination
import com.project.dewa.viewmodel.MainViewModelDestination
import kotlinx.android.synthetic.main.activity_desa_wisata.*
import kotlinx.android.synthetic.main.activity_desa_wisata.btn_cari
import kotlinx.android.synthetic.main.activity_desa_wisata.btn_clear
import kotlinx.android.synthetic.main.activity_desa_wisata.destiny_recycler_view
import kotlinx.android.synthetic.main.activity_desa_wisata.not_found
import kotlinx.android.synthetic.main.activity_desa_wisata.progressbar
import kotlinx.android.synthetic.main.activity_desa_wisata.search_view
import kotlinx.android.synthetic.main.activity_desa_wisata.toolbar_wisata
import kotlinx.android.synthetic.main.activity_desa_wisata.txv_jumlah_destinasi

class DesaWisataActivity : AppCompatActivity() {
    private val destination = ArrayList<ModelDestination>()

    lateinit var destinationAdapter : AirTerjunAdapter
    lateinit var mainViewModel : MainViewModelDestination
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desa_wisata)
        toolbar_wisata.title = "Daftar Desa Wisata di Lombok"
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
        destinationAdapter = AirTerjunAdapter(destination)
        destinationAdapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModelDestination::class.java
        )

        showLoading(true)

        mainViewModel.setDestination("Desa Wisata")
        destiny_recycler_view.setHasFixedSize(true)
        destiny_recycler_view.layoutManager = GridLayoutManager(
            this,
            2,
            RecyclerView.VERTICAL,
            false
        )
        destiny_recycler_view.adapter = destinationAdapter

        mainViewModel.getDestination().observe(this, Observer { destination ->
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
                        val a = search_view.adapter.getItem(i) as ModelDestination
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
            progressbar_title.visibility = View.VISIBLE
        }
        else{
            progressbar.visibility= View.GONE
            progressbar_title.visibility = View.GONE
        }
    }
}