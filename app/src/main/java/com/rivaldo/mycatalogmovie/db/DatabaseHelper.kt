package com.rivaldo.mycatalogmovie.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rivaldo.mycatalogmovie.db.DatabaseContract.FavoriteColumns.Companion.TABLE_MOVIE
import com.rivaldo.mycatalogmovie.db.DatabaseContract.FavoriteTv.Companion.TABLE_TV

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbfavoriteapp"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE $TABLE_MOVIE" +
                " (${DatabaseContract.FavoriteColumns._ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.FavoriteColumns.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.DESCRIPTION} TEXT , "+
                " ${DatabaseContract.FavoriteColumns.PHOTO} TEXT NOT NULL)"

        private val SQL_CREATE_TABLE_TV = "CREATE TABLE $TABLE_TV" +
                " (${DatabaseContract.FavoriteTv._ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.FavoriteTv.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteTv.DESCRIPTION} TEXT, "+
                " ${DatabaseContract.FavoriteTv.PHOTO} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_MOVIE)
        db?.execSQL(SQL_CREATE_TABLE_TV)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TV")
        onCreate(db)

    }
}