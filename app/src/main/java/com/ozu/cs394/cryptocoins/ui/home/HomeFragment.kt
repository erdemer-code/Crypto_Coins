package com.ozu.cs394.cryptocoins.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozu.cs394.cryptocoins.BuildConfig
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.ui.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getCurrentCoinsPrice(
            BuildConfig.API_KEY,
            listOf<String>(
                "BTC", "ETH",
                "DOGE"
            ), "USD"
        )

        initObserver()
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter

    }

    private fun initObserver() {
        viewModel.currentCoinsPriceLiveData.observe(viewLifecycleOwner){
            it.forEach { coinResponse ->
                Log.e("Coin_Id",coinResponse.id.toString())
                Log.e("Coin_Name",coinResponse.name.toString())
                Log.e("Coin_Price",coinResponse.price.toString())
                Log.e("Coin_Symbol",coinResponse.symbol.toString())
                Log.e("Coin_PriceDate",coinResponse.convertedPriceDate.toString())
                Log.e("Coin_Logo",coinResponse.logo_url!!)

            }
        }
    }

}