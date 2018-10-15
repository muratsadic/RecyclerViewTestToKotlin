package com.example.oem.RssParserWithKotlin

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class RecyclerAdapter(private val context: Context, item: List<RssItem>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    companion object {


        private var itemsList: List<RssItem> = ArrayList()
        @SuppressLint("StaticFieldLeak")
        private lateinit var ctx: Context
    }

    init {

        RecyclerAdapter.ctx = context
        RecyclerAdapter.itemsList = item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(v)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rssItem: RssItem = itemsList[position]
        holder.title.setText(rssItem.title)
        holder.channelTitle.setText(rssItem.channel_title)
        holder.pubDate.setText(rssItem.pubDate)

        Glide.with(context)
                .load(rssItem.imageURL)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.photo)
    }


    override fun getItemCount() = itemsList.size


    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView
        val channelTitle: TextView
        val pubDate: TextView
        val photo: ImageView

        init {
            title = v.findViewById<View>(R.id.item_title) as TextView
            channelTitle = v.findViewById<View>(R.id.channel_title) as TextView
            pubDate = v.findViewById<View>(R.id.item_pubDate) as TextView
            photo = v.findViewById<View>(R.id.image) as ImageView

        }
    }

}