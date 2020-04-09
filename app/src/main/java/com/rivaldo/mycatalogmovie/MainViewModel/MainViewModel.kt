package com.rivaldo.mycatalogmovie.MainViewModel

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.Items.SearchedMovie
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "e10b31066b4d9cb3e4e6aa8fefed3b35"
    }

    val listMovie = MutableLiveData<ArrayList<Movie>>()
    val listFounded = MutableLiveData<ArrayList<Movie>>()


    internal fun getListMovies(language: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=$language"
        val imagerequest = "https://image.tmdb.org/t/p/w500"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    var size = list.length()

                    for (i in 0 until size) {
                        val movie = list.getJSONObject(i)
                        var id = movie.getInt("id")
                        var Title = movie.getString("title")
                        var Description = movie.getString("overview")
                        val imageurl = movie.getString("poster_path")
                        var image = "$imagerequest$imageurl"
                        val movieItems = Movie(id,image, Title, Description)

                        listItems.add(movieItems)
                    }
                    listMovie.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })

    }


    @RequiresApi(Build.VERSION_CODES.O)
    internal fun getRelease() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val todayDate = current.format(formatter)
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&primary_release_date.gte=$todayDate&primary_release_date.lte=$todayDate"
        val imagerequest = "https://image.tmdb.org/t/p/w500"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    var size = list.length()

                    for (i in 0 until size) {
                        val movie = list.getJSONObject(i)
                        var id = movie.getInt("id")
                        var Title = movie.getString("title")
                        var Description = movie.getString("overview")
                        val imageurl = movie.getString("poster_path")
                        var image = "$imagerequest$imageurl"
                        val movieItems = Movie(id,image, Title, Description)

                        listItems.add(movieItems)
                    }
                    listMovie.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })


    }

    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovie
    }

    internal fun getFounded(): LiveData<ArrayList<Movie>> {
        return listFounded
    }


    internal fun searchMovie(itemSearch: String?,language: String ) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/search/movie?api_key=$API_KEY&language=$language&query=$itemSearch"
        val imagerequest = "https://image.tmdb.org/t/p/w500"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    var size = list.length()
                    for ( i in 0 until size) {
                        val movie = list.getJSONObject(i)
                        var id = movie.getInt("id")
                        var Title = movie.getString("title")
                        var Description = movie.getString("overview")
                        val imageurl = movie.getString("poster_path")
                        var image = "$imagerequest$imageurl"
                        val Founded = Movie(id,image,Title,Description)
                            listItems.add(Founded)
                    }
                    listFounded.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())

                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())

            }
        })
    }

//    fun loadImage(url:String) : Bitmap {
//
//
//
//
//    }

}