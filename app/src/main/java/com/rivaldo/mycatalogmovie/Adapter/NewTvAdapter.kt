package com.rivaldo.mycatalogmovie.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.mycatalogmovie.Items.Tv
import com.rivaldo.mycatalogmovie.R
import kotlinx.android.synthetic.main.item_tv.view.*

class NewTvAdapter : RecyclerView.Adapter<NewTvAdapter.TvViewHolder>() {

    private val mData = ArrayList<Tv>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    fun setData(items: ArrayList<Tv>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvItems: Tv) {
            with(itemView) {
                title_tv.text = tvItems.Title
                txt_description.text = tvItems.Description
                Glide.with(this)
                    .load(tvItems.Photo)
                    .into(img_photo)
                favorite_tv_button.setOnClickListener {
                    onItemClickCallback?.onFavoriteClicked(tvItems)
                }
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(tvItems) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_tv, parent, false)
        return TvViewHolder(mView)

    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Tv)
        fun onFavoriteClicked(data: Tv)
    }

}