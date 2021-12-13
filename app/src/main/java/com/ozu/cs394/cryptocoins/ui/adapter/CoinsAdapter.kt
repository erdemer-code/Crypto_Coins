package com.ozu.cs394.cryptocoins.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.CoinItemBinding
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel

class CoinsAdapter(private val coinsList: MutableList<CoinResponseModel>,
                   private val onCoinClickListener:OnCoinClickListener?) : RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsAdapter.CoinsViewHolder =
        CoinsViewHolder(DataBindingUtil.inflate<CoinItemBinding>(LayoutInflater.from(parent.context),R.layout.coin_item,parent,false))

    override fun onBindViewHolder(holderCoins: CoinsViewHolder, position: Int) {
        holderCoins.bindView.coinData = coinsList[position]
        holderCoins.itemView.setOnClickListener {
            onCoinClickListener?.onClick(position)
        }
    }

    override fun getItemCount(): Int = coinsList.size

    class CoinsViewHolder(val bindView: CoinItemBinding): RecyclerView.ViewHolder(bindView.root){


    }
}

interface OnCoinClickListener{
    fun onClick(position: Int)
}