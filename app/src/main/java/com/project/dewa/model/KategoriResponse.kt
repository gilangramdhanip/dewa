package com.project.dewa.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KategoriResponse(
    var status: Boolean,
    var data: Array<ModelKategori>
): Parcelable