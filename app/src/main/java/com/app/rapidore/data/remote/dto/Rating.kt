package com.app.rapidore.data.remote.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize

data class Rating( val rate:String,
                   val count:Int): Parcelable
