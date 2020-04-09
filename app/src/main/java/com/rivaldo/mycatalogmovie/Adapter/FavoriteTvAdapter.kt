package com.rivaldo.mycatalogmovie.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.mycatalogmovie.Items.Tv
import com.rivaldo.mycatalogmovie.R
import com.rivaldo.mycatalogmovie.db.FavoriteHelper
import kotlinx.android.synthetic.main.item_favorite_tv.view.*

class FavoriteTvAdapter : RecyclerView.Adapter<FavoriteTvAdapter.TvViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null
    var listTv = ArrayList<Tv>()



    set(listTv) {
        if(listTv.size > 0) {
            this.listTv.clear()
        }
        this.listTv.addAll(listTv)
        notifyDataSetChanged()
    }
    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }



   inner class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tv: Tv) {
            with(itemView){
                title_fav_tv.text =  tv.Title
                fav_tv_description.text = tv.Description
                Glide.with(this)
                    .load(tv.Photo)
                    .into(image_tv)

                delete_button_tv.setOnClickListener {
                    onItemClickCallback?.onDeleteClicked(tv)
                }

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(tv) }


            }
        }

    }



    fun getListTv(listtv: ArrayList<Tv>) {
        listTv = listtv
    }

    fun removeItem(tv: Tv) {
        var position = this.listTv.indexOf(tv)
        this.listTv.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTv.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteTvAdapter.TvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_tv, parent, false)
        return TvViewHolder(view)
    }

    override fun getItemCount(): Int = this.listTv.size

    override fun onBindViewHolder(holder: FavoriteTvAdapter.TvViewHolder, position: Int) {
       holder.bind(listTv[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Tv)
        fun onDeleteClicked(data: Tv)
    }



}


