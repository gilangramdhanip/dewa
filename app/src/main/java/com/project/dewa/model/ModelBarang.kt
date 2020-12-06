package com.project.dewa.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelBarang(
    var id_barang: Int,
    var name_barang: String,
    var address_destination: String? = null,
    var desc_destination: String? = null,
    var id_kategori: String? = null,
    var img_barang: String? = null,
    var id_kabupaten: String? = null,
    var id_kecamatan: String? = null,
    var jambuka: String? = null,
    var jamtutup: String? = null,
    var id_admin: String? = null,
    var status: String? = null,
    var id_wisatawan: String? = null,
    var no_hp: String? = null,
    var harga: String? = null
) :Parcelable {
    override fun toString() = this.name_barang
}