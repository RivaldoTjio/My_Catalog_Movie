package com.rivaldo.mycatalogmovie.MainViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.Items.Tv
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModelTv : ViewModel() {

    companion object {
        private const val API_KEY = "e10b31066b4d9cb3e4e6aa8fefed3b35"
    }

    val listTv = MutableLiveData<ArrayList<Tv>>()
    val listFounded = MutableLiveData<ArrayList<Tv>>()

    internal fun getListTv(language: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Tv>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=$language"
        val imagerequest = "https://image.tmdb.org/t/p/w500"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObjects = JSONObject(result)
                    val list = responseObjects.getJSONArray("results")
                    var size = list.length()

                    for (i in 0 until size) {
                        val tv = list.getJSONObject(i)
                        var id = tv.getInt("id")
                        var Title = tv.getString("name")
                        var Description = tv.getString("overview")
                        val imageurl = tv.getString("poster_path")
                        val image = "$imagerequest$imageurl"
                        val tvItems = Tv(id,image, Title, Description)
                        listItems.add(tvItems)
                    }
                    listTv.postValue(listItems)
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

    internal fun getTv(): LiveData<ArrayList<Tv>> {
        return listTv
    }

    internal fun getFounded(): LiveData<ArrayList<Tv>> {
        return listFounded
    }



    internal fun searchTv(itemSearch: String?, language: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Tv>()
        val url = "https://api.themoviedb.org/3/search/tv?api_key=$API_KEY&language=$language&query=$itemSearch"
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
                        var Title = movie.getString("name")
                        var Description = movie.getString("overview")
                        val imageurl = movie.getString("poster_path")
                        var image = "$imagerequest$imageurl"
                        val Founded = Tv(id,image,Title,Description)
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

}