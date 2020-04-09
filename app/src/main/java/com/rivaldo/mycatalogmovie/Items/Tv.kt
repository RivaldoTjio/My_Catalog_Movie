package com.rivaldo.mycatalogmovie.Items

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tv(
    var id: Int,
    var Photo: String,
    var Title: String,
    var Description: String
) : Parcelable