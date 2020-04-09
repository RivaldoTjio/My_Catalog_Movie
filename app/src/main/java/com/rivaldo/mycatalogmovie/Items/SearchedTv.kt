package com.rivaldo.mycatalogmovie.Items

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchedTv(
    var id: String,
    var title: String,
    var Description: String,
    var Photo: String
) : Parcelable