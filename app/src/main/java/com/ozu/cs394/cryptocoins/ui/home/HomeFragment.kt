package com.ozu.cs394.cryptocoins.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozu.cs394.cryptocoins.BuildConfig
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.HomeFragmentBinding
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel
import com.ozu.cs394.cryptocoins.ui.adapter.CoinsAdapter
import com.ozu.cs394.cryptocoins.ui.adapter.OnCoinClickListener
import com.ozu.cs394.cryptocoins.util.SharedPreferenceManager

class HomeFragment : Fragment() {

    private lateinit var adapter: CoinsAdapter
    private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val spManager = SharedPreferenceManager

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var isFetched: Boolean? = null
    private val coinsListForRV = mutableListOf<CoinResponseModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        spManager.putData(true, "SAVED")

        if (isFetched == null) {
            viewModel.getCurrentCoinsPrice(
                BuildConfig.NOMICS_API_KEY,
                listOf<String>(
                    "BTC", "ETH", "BNB", "SOL", "ADA", "XRP", "AVAX",
                    "DOGE", "LTC", "MATIC", "XLM", "EGLD", "TRX", "FTM",
                    "MANA", "FIL", "ATOM", "ALGO", "XTZ", "UNI", "LINK",
                    "DAI", "VET", "ICP", "XMR", "XTZ", "USDC", "AAVE",
                    "EOS", "LRC", "QNT", "MKR", "NEO", "HEX", "USDT",
                    "LUNA", "CRO", "NEAR", "BUSD", "HBAR", "ETC", "GRT"

                ), "USD"
            )
        } else {
            setRVAdapter(coinsListForRV)
        }



        binding.toolbar.inflateMenu(R.menu.toolbar_menu)
        binding.toolbar.setOnMenuItemClickListener { p0 ->
            when (p0?.itemId) {
                R.id.item_exit -> {
                    spManager.removeSharedPreferences()
                    requireActivity().finishAffinity()
                }
            }
            false
        }
        binding.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        initObserver()

    }


    private fun initObserver() {
        coinsListForRV.clear()
        viewModel.currentCoinsPriceLiveData.observe(viewLifecycleOwner) {
            it.forEach { coinResponse ->
                coinsListForRV.add(coinResponse)
            }
            isFetched = true
            setRVAdapter(coinsListForRV)
        }
        viewModel.homeLoadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbHomeLoading.visibility = View.INVISIBLE
                binding.recyclerViewHome.visibility = View.VISIBLE
            }
        }
    }

    private fun setRVAdapter(coinList: MutableList<CoinResponseModel>) {
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHome.layoutManager = layoutManager

        adapter = CoinsAdapter(coinList, object : OnCoinClickListener {
            override fun onClick(position: Int) {
                val bundle = bundleOf("coin" to coinList[position])
                findNavController().navigate(R.id.action_homeFragment_to_coinDetailFragment, bundle)
            }
        })
        binding.recyclerViewHome.adapter = adapter

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
