package com.rivaldo.mycatalogmovie.helper

import android.database.Cursor
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.Items.Tv
import com.rivaldo.mycatalogmovie.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(favoriteMovie: Cursor): ArrayList<Movie> {
        val movieList = ArrayList<Movie>()
        favoriteMovie.moveToFirst()
        while (favoriteMovie.moveToNext()) {
            val id = favoriteMovie.getInt(favoriteMovie.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
            val title = favoriteMovie.getString(favoriteMovie.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE))
            val description = favoriteMovie.getString(favoriteMovie.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DESCRIPTION))
            val photo = favoriteMovie.getString(favoriteMovie.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.PHOTO))
            movieList.add(Movie(id,photo,title,  description))
        }
        return movieList
    }
    fun mapCursorToArrayListTv(favoriteTv: Cursor): ArrayList<Tv> {
        val tvList = ArrayList<Tv>()
        favoriteTv.moveToFirst()
        while (favoriteTv.moveToNext()) {
            val id = favoriteTv.getInt(favoriteTv.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
            val title = favoriteTv.getString(favoriteTv.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE))
            val description = favoriteTv.getString(favoriteTv.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DESCRIPTION))
            val photo = favoriteTv.getString(favoriteTv.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.PHOTO))
            tvList.add(Tv(id,photo,title,  description))
        }
        return tvList
    }
}