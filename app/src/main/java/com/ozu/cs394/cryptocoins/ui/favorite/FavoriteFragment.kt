package com.ozu.cs394.cryptocoins.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.FavoriteFragmentBinding
import com.ozu.cs394.cryptocoins.room.CoinsDAOImpl
import com.ozu.cs394.cryptocoins.room.CoinsDatabase
import com.ozu.cs394.cryptocoins.ui.adapter.FavoriteCoinAdapter
import com.ozu.cs394.cryptocoins.ui.adapter.OnFavoriteCoinClickListener
import com.ozu.cs394.cryptocoins.ui.home.HomeViewModel
import kotlinx.coroutines.*

class FavoriteFragment : Fragment() {


    private lateinit var viewModel: FavoriteViewModel
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        viewModel.getAllFavoriteCoins(requireContext())
        dataObserver()
    }

    private fun dataObserver() {
        viewModel.favoriteCoinsLiveData.observe(viewLifecycleOwner){
            binding.rvFavoriteCoins.layoutManager = LinearLayoutManager(requireContext())
            binding.rvFavoriteCoins.adapter = FavoriteCoinAdapter(it,object :OnFavoriteCoinClickListener{
                override fun onClick(position: Int) {
                    val bundle = bundleOf("coin" to it[position])
                    findNavController().navigate(R.id.action_favoriteFragment_to_coinDetailFragment, bundle)
                }
            })

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}