package com.project.dewa.activities

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.project.dewa.BuildConfig
import com.project.dewa.R
import com.project.dewa.model.ModelDestination
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val EXTRA_DETAIl = "extra_detail"
    }

    private var ambil :Int = 0

    private var destination : ModelDestination? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        destination = intent.getParcelableExtra(EXTRA_DETAIl) as? ModelDestination
        //Load Destination

        destination?.id_destination
        placeNameTextView.text = destination?.name_destination
        addressTextView.text = destination?.address_destination
        placeDescTextView.text = destination?.desc_destination
        categoryTextView.text = destination?.id_kategori
        if(destination?.img_destination == ""){
            imageViewPager.setImageResource(R.drawable.default_img)
        }else{
            Glide.with(baseContext)
                .load("http://siwita-lombok.monster/rest_api/rest-server-sig/assets/foto/" + destination?.img_destination)
                .into(imageViewPager)
        }
//        txv_cat.text = destination?.id_kategori
//        txv_kabupaten.text = destination?.id_kabupaten
//        txv_kecamatan.text = destination?.id_kecamatan
        jamBuka.text = destination?.jambuka
        jamTutup.text = destination?.jamtutup

        directionGoogleMaps.setOnClickListener {
            val uri =
                "http://maps.google.com/maps?q=loc:"+destination!!.lat_destination +","+ destination!!.lng_destination
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
        textViewShare.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, destination?.id_destination)
                var shareMessage = "\nWisata ${destination?.name_destination} berada di Kabupaten ${destination?.id_kabupaten} dan Kecamatan ${destination?.id_kecamatan}" +
                        "\nSelengkapnya ada di Aplikasi Siwita, Yuk Download\n\n"
                shareMessage =
                    """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "Pilih Metode"))
            } catch (e: Exception) {
                //e.toString();
            }
        }

//        btn_go_maps.setOnClickListener(this)
        initUI(savedInstanceState)
    }

    private fun initUI(savedInstanceState: Bundle?) {

        initToolbar()

        val toolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        toolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )

        placeMapView.onCreate(savedInstanceState)
        placeMapView.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.setMinZoomPreference(12f)
        val ny = LatLng(
            destination!!.lat_destination!!.toDouble(),
            destination!!.lng_destination!!.toDouble()
        )

        val markerOptions = MarkerOptions()
        markerOptions.position(ny)
        googleMap.addMarker(markerOptions)

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny))
    }

    public override fun onResume() {
        placeMapView.onResume()
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
        placeMapView.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()
        placeMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        placeMapView.onLowMemory()
    }

    private fun initToolbar() {

        val toolbar: Toolbar? = findViewById<Toolbar>(R.id.toolbar)
        toolbar!!.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.md_white_1000
                ), PorterDuff.Mode.SRC_ATOP
            )
        }

        toolbar.title = destination!!.name_destination

        try {
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000))
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

    }


}