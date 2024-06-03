package com.app.rapidore.data.remote.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductItem(
    val id:Int?=null,
    val price: String,
    val description: String,
    val category: String,
    val title: String,
    val image: String,
    val rating: Rating,
): Parcelable

