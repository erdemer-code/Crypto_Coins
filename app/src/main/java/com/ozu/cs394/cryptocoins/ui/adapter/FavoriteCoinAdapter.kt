package com.ozu.cs394.cryptocoins.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ozu.cs394.cryptocoins.databinding.CoinItemBinding
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel

class FavoriteCoinAdapter(
    private val onFavoriteCoinClickListener: OnFavoriteCoinClickListener
) : ListAdapter<CoinResponseModel,FavoriteCoinAdapter.FavoriteCoinsViewHolder>(DiffCallback()) {

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
        holder.binding.coinData = getItem(position)
        holder.itemView.setOnClickListener {
            onFavoriteCoinClickListener.onClick(position)
        }
    }

}


class DiffCallback: DiffUtil.ItemCallback<CoinResponseModel>(){
    override fun areItemsTheSame(oldItem: CoinResponseModel, newItem: CoinResponseModel): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: CoinResponseModel,
        newItem: CoinResponseModel
    ): Boolean = oldItem == newItem

}

interface OnFavoriteCoinClickListener {
    fun onClick(position: Int)
}