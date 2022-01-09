package com.ozu.cs394.cryptocoins.ui.favorite

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.FavoriteFragmentBinding
import com.ozu.cs394.cryptocoins.room.CoinsDAOImpl
import com.ozu.cs394.cryptocoins.room.CoinsDatabase
import com.ozu.cs394.cryptocoins.ui.adapter.FavoriteCoinAdapter
import com.ozu.cs394.cryptocoins.ui.adapter.OnFavoriteCoinClickListener
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.launch


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
        viewModel.favoriteCoinsLiveData.observe(viewLifecycleOwner) { list ->
            binding.rvFavoriteCoins.layoutManager = LinearLayoutManager(requireContext())
            val adapter = FavoriteCoinAdapter(object : OnFavoriteCoinClickListener {
                override fun onClick(position: Int) {
                    val bundle = bundleOf("coin" to list[position])
                    findNavController().navigate(
                        R.id.action_favoriteFragment_to_coinDetailFragment,
                        bundle
                    )
                }
            })
            adapter.submitList(list)
            binding.rvFavoriteCoins.adapter = adapter


            val itemTouchHelperCallback =
                object :
                    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean = false

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        var clickedUndo = false
                        Snackbar.make(
                            requireView(),
                            "Do you want to delete this coin from your favorites",
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction("UNDO") {
                                clickedUndo = true
                                adapter.notifyItemChanged(viewHolder.absoluteAdapterPosition)
                            }
                            .addCallback(object : Snackbar.Callback() {
                                override fun onDismissed(
                                    transientBottomBar: Snackbar?,
                                    event: Int
                                ) {
                                    super.onDismissed(transientBottomBar, event)
                                    if (!clickedUndo) {
                                        lifecycleScope.launch {
                                            val db =
                                                CoinsDAOImpl(CoinsDatabase.getInstance(requireContext()))
                                            db.deleteCoin(list[viewHolder.absoluteAdapterPosition])
                                            Log.e("AllList",db.getAllFavoriteCoins().toString())
                                            adapter.submitList(db.getAllFavoriteCoins())
                                        }
                                    }

                                }
                            }
                            )
                            .show()


                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {
                        RecyclerViewSwipeDecorator.Builder(
                            requireActivity(),
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                            .addBackgroundColor(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.red
                                )
                            )
                            .addActionIcon(R.drawable.ic_trash_bin)
                            .create()
                            .decorate()
                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                    }

                }
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(binding.rvFavoriteCoins)

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}