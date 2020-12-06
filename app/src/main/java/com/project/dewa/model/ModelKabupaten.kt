package com.project.dewa.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelKabupaten(
    val id_kabupaten : String,
    val name_kabupaten : String
) : Parcelable