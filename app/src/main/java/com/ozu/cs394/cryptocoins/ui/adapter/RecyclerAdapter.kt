package com.ozu.cs394.cryptocoins.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ozu.cs394.cryptocoins.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    private var titles = arrayOf("Bitcoin", "Ethereum", "Binance","LiteCoin", "Shiba")
    private var shortTitles = arrayOf("BTC", "ETH", "BNB","LTC", "SHB")
    private var images = intArrayOf(R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background)
    private var prices = doubleArrayOf(60000.12,30000.25,8000.30,3000.01,987.31)
    private var dailyChanges = doubleArrayOf(1.23,-2.32,3.45,-4.51,5.63)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemShortTitle.text = shortTitles[position]
        holder.itemImage.setImageResource(images[position])
        holder.itemPrice.text = prices[position].toString()
        holder.itemDailyChange.text = dailyChanges[position].toString()
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView = itemView.findViewById(R.id.item_title)
        var itemShortTitle: TextView = itemView.findViewById(R.id.item_short_title)
        var itemImage: ImageView = itemView.findViewById(R.id.item_image)
        var itemPrice: TextView = itemView.findViewById(R.id.item_price)
        var itemDailyChange: TextView = itemView.findViewById(R.id.item_daily_change)

    }
}