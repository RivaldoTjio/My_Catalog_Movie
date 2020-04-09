package com.rivaldo.mycatalogmovie.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.rivaldo.mycatalogmovie.db.DatabaseContract.FavoriteColumns.Companion.TABLE_MOVIE
import com.rivaldo.mycatalogmovie.db.DatabaseContract.FavoriteColumns.Companion.TITLE
import com.rivaldo.mycatalogmovie.db.DatabaseContract.FavoriteColumns.Companion._ID
import com.rivaldo.mycatalogmovie.db.DatabaseContract.FavoriteTv.Companion.TABLE_TV
import org.jetbrains.anko.db.insert
import java.sql.SQLException

class FavoriteHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)

    private lateinit var database: SQLiteDatabase
    companion object {
        private const val DATABASE_TABLE = TABLE_MOVIE
        private const val DATABASE_TV = TABLE_TV
        private var INSTANCE: FavoriteHelper? = null
        fun getInstance(context: Context): FavoriteHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = FavoriteHelper(context)
                    }
                }
            }
            return INSTANCE as FavoriteHelper
        }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)

    }



    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()

    }

    fun queryAll(type: String): Cursor {
        if (type == "movie") {
            return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$_ID ASC"
            )
        } else {
            return database.query(
                DATABASE_TV,
                null,
                null,
                null,
                null,
                null,
                null)
        }
    }

    fun queryTv(): Cursor {
        return database.query(
            TABLE_TV,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?, type: String): Long {

        if (type == "movie") {
            Log.d("TAG", "Movie Data berhasil ditambahkan")
            return database.insert(DATABASE_TABLE, null, values)
        } else {
            Log.d("TAG", "Tv Data berhasil ditambahkan ")
            return database.insert(DATABASE_TV, null, values)
        }
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
    fun deleteByTitle(title: String, type: String): Int {
        if (type == "movie") {
            return database.delete(DATABASE_TABLE, "$TITLE = '$title'", null)
        } else {
            return database.delete(DATABASE_TV, "$TITLE = '$title'", null)
        }
    }

}