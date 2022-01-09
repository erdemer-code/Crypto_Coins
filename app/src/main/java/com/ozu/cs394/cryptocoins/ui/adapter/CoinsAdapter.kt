package com.ozu.cs394.cryptocoins.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.CoinItemBinding
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel

class CoinsAdapter(private val coinsList: MutableList<CoinResponseModel>,
                   private val onCoinClickListener:OnCoinClickListener?) : RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder>() , Filterable{

    private var coinListFull = coinsList;

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

    override fun getFilter(): Filter = searchFilter
        private val searchFilter = object:Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<CoinResponseModel>()
                if (constraint == null || constraint.isEmpty())
                    filteredList.addAll(coinListFull)
                else {
                    val filterPattern = constraint.toString().lowercase().trim();
                    coinListFull.forEach { coin->
                        if(coin.symbol?.lowercase()?.contains(filterPattern) == true)
                            filteredList.add(coin);
                    }
                }
                val results = FilterResults();
                results.values = filteredList;
                return results;
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                coinsList.clear();
                coinsList.addAll(results?.values as List<CoinResponseModel>)
                notifyDataSetChanged()
            }
        };
    }


interface OnCoinClickListener{
    fun onClick(position: Int)
}