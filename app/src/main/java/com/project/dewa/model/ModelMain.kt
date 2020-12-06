package com.project.dewa.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelMain(
    val txtName : String,
    val imageSource : Int
) : Parcelable