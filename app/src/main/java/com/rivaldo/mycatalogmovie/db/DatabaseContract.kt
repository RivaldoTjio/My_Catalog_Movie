package com.rivaldo.mycatalogmovie.db

import android.provider.BaseColumns

class DatabaseContract {


    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_MOVIE = "movie"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION =  "description"
            const val PHOTO = "photo"
            const val TABLE_TV = "tv"
        }

    }

    internal class FavoriteTv : BaseColumns {
        companion object {
            const val TABLE_TV = "tv"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION =  "description"
            const val PHOTO = "photo"
        }
    }
}