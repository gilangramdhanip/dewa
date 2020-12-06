package com.project.dewa.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelKecamatan(
    val id_kecamatan : String,
    val name_kecamatan : String,
    val id_kabupaten : String
) : Parcelable