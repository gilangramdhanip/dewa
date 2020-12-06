package com.project.dewa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.dewa.activities.*
import com.project.dewa.adapter.MainAdapter
import com.project.dewa.model.ModelMain
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MainAdapter.onSelectData {

    private lateinit var mdMainMenu : ModelMain
    var lsMainMenu = ArrayList<ModelMain>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var mLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rvMainMenu.layoutManager = mLayoutManager

        rvMainMenu.setHasFixedSize(true)
        setMenu()
    }

    override fun onResume() {
        super.onResume()

        getToday()

    }

    fun getToday(){
        var date = Calendar.getInstance().time
        var day = android.text.format.DateFormat.format("EEEE", date)
        var tanggal = android.text.format.DateFormat.format("d MMMM yyyy", date)
        var waktuFix = "$day, $tanggal"
        tvDate.text = waktuFix
    }

    fun setMenu(){
        mdMainMenu = ModelMain("Air Terjun", R.drawable.ic_airterjun)
        lsMainMenu.add(mdMainMenu)
        mdMainMenu = ModelMain("Gili", R.drawable.ic_gili)
        lsMainMenu.add(mdMainMenu)
        mdMainMenu = ModelMain("Taman", R.drawable.ic_taman)
        lsMainMenu.add(mdMainMenu)
        mdMainMenu = ModelMain("Desa Wisata", R.drawable.ic_village)
        lsMainMenu.add(mdMainMenu)
        mdMainMenu = ModelMain("Umkm", R.drawable.ic_shooping_bag)
        lsMainMenu.add(mdMainMenu)
        mdMainMenu = ModelMain("Pantai", R.drawable.ic_beach)
        lsMainMenu.add(mdMainMenu)

        var myAdapter = MainAdapter(lsMainMenu, this)
        rvMainMenu.adapter = myAdapter
    }

    override fun onSelected(mdlMain: ModelMain?) {
        when (mdlMain!!.txtName) {
            "Air Terjun" -> startActivityForResult(
                Intent(
                    this@MainActivity,
                    AirTerjunActivity::class.java
                ), 1
            )
            "Gili" -> startActivityForResult(
                Intent(
                    this@MainActivity,
                    GiliActivity::class.java
                ), 1
            )
            "Taman" -> startActivityForResult(
                Intent(
                    this@MainActivity,
                    TamanActivity::class.java
                ), 1
            )
            "Desa Wisata" -> startActivityForResult(
                Intent(
                    this@MainActivity,
                    DesaWisataActivity::class.java
                ), 1
            )

            "Umkm" -> startActivityForResult(
                Intent(
                    this@MainActivity,
                    UmkmActivity::class.java
                ), 1
            )

            "Pantai" -> startActivityForResult(
                Intent(
                    this@MainActivity,
                    PantaiActivity::class.java
                ), 1
            )
        }
    }
}