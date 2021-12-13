package com.ozu.cs394.cryptocoins.ui.coin_detail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.ozu.cs394.cryptocoins.BuildConfig
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.databinding.CoinDetailFragmentBinding
import com.github.mikephil.charting.utils.MPPointF
import android.graphics.Canvas
import com.github.mikephil.charting.components.IMarker
import com.ozu.cs394.cryptocoins.extension.checkValuePositiveOrNegative
import com.ozu.cs394.cryptocoins.extension.downloadFromUrl
import com.ozu.cs394.cryptocoins.extension.putCorrectArrow

class CoinDetailFragment : Fragment() {

    private val args: CoinDetailFragmentArgs by navArgs()
    private lateinit var viewModel: CoinDetailViewModel
    private var _binding: CoinDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val priceTimeSeries = mutableListOf<Entry>()
    val timeList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        _binding = CoinDetailFragmentBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CoinDetailViewModel::class.java]

        viewModel.getCoinDetailPrices(
            "assets",
            BuildConfig.LUNARCRUSH_API_KEY,
            args.coin?.symbol ?: "",
            "30",
            "day"
        )
        initObserver()
        initViewSetup()


    }

    private fun initViewSetup() {
        binding.apply {
            ivCoinDetail.downloadFromUrl(args.coin?.logo_url)
            tvCoinDetailName.text = args.coin?.name
            tvCoinDetailSymbol.text = args.coin?.symbol
            tvCoinDetailPrice.text = "$ ${args.coin?.roundedPrice}"
            tvCoinDetailChangeValue.checkValuePositiveOrNegative(args.coin?.day1?.formattedPriceChange!!)
            ivCoinDetailValueArrow.putCorrectArrow(args.coin?.day1?.formattedPriceChange!!)
        }

    }

    private fun initObserver() {
        viewModel.detailLoadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbCoinDetailLoading.visibility = View.INVISIBLE
                binding.clCoinDetailMainBox.visibility = View.VISIBLE
            }
        }


        viewModel.coinDetailPriceLiveData.observe(viewLifecycleOwner) {
            it.forEachIndexed { index, timeSeriesData ->
                timeList.add(timeSeriesData.convertedTime)
                priceTimeSeries.add(Entry(index.toFloat(), timeSeriesData.close!!.toFloat()))
            }

            setLineChart()
        }
    }

    private fun setLineChart() {
        val dataSet = LineDataSet(priceTimeSeries, "").apply {
            this.color = Color.WHITE
            this.valueTextColor = Color.WHITE
            this.valueTextSize = 10f
            this.setDrawValues(false)
            this.setDrawFilled(true)
            this.fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_bg)
            this.valueFormatter = XaxisFormatter()

        }

        val dataSets: ArrayList<ILineDataSet> = arrayListOf()
        dataSets.add(dataSet)
        val lineData: LineData = LineData(dataSets)


        val markerView = CustomMarkerView(requireContext(), R.layout.marker_view_graph)

        binding.lineChart.markerView = markerView

        binding.lineChart.apply {
            animateX(2000)
            data = lineData
            invalidate()
            xAxis.valueFormatter = XaxisFormatter()
            xAxis.isAvoidFirstLastClippingEnabled
            xAxis.mLabelHeight = 150
            xAxis.mLabelRotatedHeight = 200
            xAxis.labelRotationAngle = 90f
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.textSize = 12f
            xAxis.textColor = Color.WHITE
            description.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.valueFormatter = YaxisFormatter()
            axisLeft.textColor = Color.WHITE
            legend.isEnabled = false

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    inner class XaxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return timeList[value.toInt()]
        }

        override fun getFormattedValue(value: Float): String {
            return "$" + String.format("%.2f", value)
        }

    }

    inner class YaxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return "$" + String.format("%.2f", value)
        }
    }

    inner class CustomMarkerView(context: Context?, layoutResource: Int) : MarkerView(
        context,
        layoutResource
    ),IMarker {
        private var tvContent: TextView? = null
        private var mOffset: MPPointF? = null
        private val uiScreenWidth = resources.displayMetrics.widthPixels

        init {
            tvContent =  findViewById(R.id.tvMarkerContent)
        }

        override fun refreshContent(e: Entry, highlight: Highlight?) {
            tvContent?.text = "Price: $ "+ String.format("%.2f", e.y) +"\nTime: ${timeList[e.x.toInt()]}"
            super.refreshContent(e, highlight);
        }

        override fun getOffset(): MPPointF? {
            if (mOffset == null) {
                // center the marker horizontally and vertically
                mOffset = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
            }
            return mOffset
        }

        override fun draw(canvas: Canvas, posx: Float, posy: Float) {
            // Check marker position and update offsets.
            var posx = posx
            val w = width
            if (uiScreenWidth - posx - w < w) {
                posx -= w.toFloat()
            }

            // translate to the correct position and draw
            canvas.translate(posx, posy)
            draw(canvas)
            canvas.translate(-posx, -posy)
        }


    }
}

