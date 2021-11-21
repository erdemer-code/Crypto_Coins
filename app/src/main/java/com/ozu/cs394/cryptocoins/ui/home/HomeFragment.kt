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
import com.ozu.cs394.cryptocoins.databinding.HomeFragmentBinding
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel
import com.ozu.cs394.cryptocoins.ui.adapter.CoinsAdapter
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater,container,false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getCurrentCoinsPrice(
            BuildConfig.API_KEY,
            listOf<String>(
                "BTC", "ETH","BNB","SOL","ADA","XRP","AVAX",
                "DOGE","SHIB","LTC","MATIC","XLM","EGLD","TRX","FTM",
                "MANA","FIL","ATOM","ALGO","XTZ"
            ), "USD"
        )

        initObserver()


    }

    private fun initObserver() {
        val coinsListForRV = mutableListOf<CoinResponseModel>()
        viewModel.currentCoinsPriceLiveData.observe(viewLifecycleOwner){
            it.forEach { coinResponse ->
            coinsListForRV.add(coinResponse)
            }

            setRVAdapter(coinsListForRV)
        }
        viewModel.homeLoadingLiveData.observe(viewLifecycleOwner){
            binding.pbHomeLoading.visibility = View.INVISIBLE
            binding.recyclerViewHome.visibility = View.VISIBLE
        }
    }
    private fun setRVAdapter(coinList:MutableList<CoinResponseModel>){
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHome.layoutManager = layoutManager

        adapter = CoinsAdapter(coinList,null)
        binding.recyclerViewHome.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
