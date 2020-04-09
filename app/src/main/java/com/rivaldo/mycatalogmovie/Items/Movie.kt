package com.rivaldo.mycatalogmovie.Items

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var Photo: String,
    var Title: String,
    var Description: String
) : Parcelable