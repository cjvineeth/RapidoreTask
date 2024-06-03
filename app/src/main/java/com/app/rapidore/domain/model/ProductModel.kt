package com.app.rapidore.domain.model

import android.os.Parcelable
import com.app.rapidore.data.remote.dto.Rating
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val id:Int?=null,
    val price: String,
    val description: String,
    val category: String,
    val title: String,
    val image: String,
    val rating: Rating,
):Parcelable