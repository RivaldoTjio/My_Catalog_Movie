package com.rivaldo.mycatalogmovie

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.rivaldo.mycatalogmovie.Items.Movie
import com.rivaldo.mycatalogmovie.db.FavoriteHelper
import com.rivaldo.mycatalogmovie.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL


internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    private lateinit var favoriteHelper: FavoriteHelper
    var List = ArrayList<Movie>()


    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {
//        val identityToken = Binder.clearCallingIdentity()
//        loadFavoriteMovie()
//        for (i in 0 until List.size){
//            try {
//                val url = URL(List[i].Photo)
//                var content = url.content as InputStream
//                var d : Drawable = Drawable.createFromStream(content, "src")
//                mWidgetItems.add(drawableToBitmap(d))
//            }
//            catch (e:Exception) {ndr
//                Log.d("TAG", e.toString())
//            }
//            Binder.restoreCallingIdentity(identityToken)
//        }
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        // load data dari database movie
        val identityToken = Binder.clearCallingIdentity()
        loadFavoriteMovie()
        for (i in 0 until List.size){
            try {
                val url = URL(List[i].Photo)
                var content = url.content as InputStream
                var d : Drawable = Drawable.createFromStream(content, "src")
                //mWidgetItems.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
                mWidgetItems.add(drawableToBitmap(d))
            }
            catch (e:Exception) {
                    Log.d("TAG", e.toString())

            }

            Binder.restoreCallingIdentity(identityToken)



        }



    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageViewWidget, mWidgetItems[position])


        val extras = bundleOf(FavoriteMovieWidget.EXTRA_ITEM to position)
        val fillInIntent = Intent().putExtras(extras)


        rv.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent)
        return rv

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }

    private fun loadFavoriteMovie() {
        favoriteHelper = FavoriteHelper.getInstance(mContext.applicationContext)
        favoriteHelper.open()
        GlobalScope.launch(Dispatchers.Main) {

            val deferredList = async(Dispatchers.IO) {
                val cursor =  favoriteHelper.queryAll("movie")
                MappingHelper.mapCursorToArrayList(cursor)
            }
           List = deferredList.await()

        }
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


}