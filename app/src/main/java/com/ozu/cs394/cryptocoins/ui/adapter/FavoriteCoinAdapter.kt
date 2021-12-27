package com.ozu.cs394.cryptocoins.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ozu.cs394.cryptocoins.databinding.CoinItemBinding
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel

class FavoriteCoinAdapter(
    private val favoriteCoins: List<CoinResponseModel>,
    private val onFavoriteCoinClickListener: OnFavoriteCoinClickListener
) : RecyclerView.Adapter<FavoriteCoinAdapter.FavoriteCoinsViewHolder>() {

     class FavoriteCoinsViewHolder private constructor(val binding: CoinItemBinding ): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): FavoriteCoinsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoinItemBinding.inflate(layoutInflater,parent,false)
                return FavoriteCoinsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCoinsViewHolder = FavoriteCoinsViewHolder.from(parent)

    override fun onBindViewHolder(holder: FavoriteCoinsViewHolder, position: Int) {
        holder.binding.coinData = favoriteCoins[position]
        holder.itemView.setOnClickListener {
            onFavoriteCoinClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = favoriteCoins.size
}

class DiffCallback: DiffUtil.ItemCallback<CoinResponseModel>() {
    override fun areItemsTheSame(oldItem: CoinResponseModel, newItem: CoinResponseModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CoinResponseModel,
        newItem: CoinResponseModel
    ): Boolean {
        return oldItem == newItem
    }
}

interface OnFavoriteCoinClickListener {
    fun onClick(position: Int)
}