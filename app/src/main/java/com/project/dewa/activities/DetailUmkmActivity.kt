package com.project.dewa.activities

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.project.dewa.R
import com.project.dewa.model.ModelBarang
import com.project.dewa.utils.Utils
import kotlinx.android.synthetic.main.activity_detail_umkm.*
import kotlinx.android.synthetic.main.activity_detail_umkm.addressTextView
import kotlinx.android.synthetic.main.activity_detail_umkm.jamBuka
import kotlinx.android.synthetic.main.activity_detail_umkm.jamTutup
import kotlinx.android.synthetic.main.activity_detail_umkm.phoneTextView
import kotlinx.android.synthetic.main.activity_detail_umkm.placeDescTextView
import kotlinx.android.synthetic.main.activity_detail_umkm.toolbar
import java.text.NumberFormat
import java.util.*

class DetailUmkmActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DETAIl = "extra_detail"
    }

    private var ambil :Int = 0

    private var barang : ModelBarang? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_umkm)

        initData()

        initToolbar()
    }

    private fun initData(){
        barang = intent.getParcelableExtra(DetailUmkmActivity.EXTRA_DETAIl) as? ModelBarang
        //Load Destination

        barang?.id_barang
        placeDescTextView.text = barang?.name_barang
        addressTextView.text = barang?.address_destination
        placeDescTextView.text = barang?.desc_destination

        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat =
            NumberFormat.getCurrencyInstance(localeID)
        harga.text = formatRupiah.format(barang!!.harga!!.toDouble())
        phoneTextView.text = barang!!.no_hp
        if(barang?.img_barang == ""){
            placeImageView.setImageResource(R.drawable.default_img)
        }else{
            Glide.with(baseContext)
                .load("http://siwita-lombok.monster/rest_api/rest-server-sig/assets/foto/" + barang?.img_barang)
                .into(placeImageView)
        }
        jamBuka.text = barang?.jambuka
        jamTutup.text = barang?.jamtutup

//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        textViewShare.setOnClickListener {
//            try {
//                val shareIntent = Intent(Intent.ACTION_SEND)
//                shareIntent.type = "text/plain"
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, barang?.name_barang)
//                var shareMessage = "\nWisata ${barang?.name_barang} berada di Kabupaten ${barang?.id_kabupaten} dan Kecamatan ${barang?.id_kecamatan}" +
//                        "\nSelengkapnya ada di Aplikasi Siwita, Yuk Download\n\n"
//                shareMessage =
//                    """
//                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
//                    """.trimIndent()
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
//                startActivity(Intent.createChooser(shareIntent, "Pilih Metode"))
//            } catch (e: Exception) {
//                //e.toString();
//            }
//        }
    }

    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = barang!!.name_barang

        try {
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.md_white_1000))
        } catch (e: Exception) {
            Log.e("TEAMPS", "Can't set color.")
        }

        try {
            setSupportActionBar(toolbar)
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set support action bar.")
        }

        try {
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set display home as up enabled.")
        }

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)

        if (Utils.isRTL) {
            collapsingToolbarLayout.collapsedTitleGravity = Gravity.END
            collapsingToolbarLayout.expandedTitleGravity = Gravity.END or Gravity.BOTTOM
        } else {
            collapsingToolbarLayout.collapsedTitleGravity = Gravity.START
            collapsingToolbarLayout.expandedTitleGravity = Gravity.START or Gravity.BOTTOM
        }

    }
}