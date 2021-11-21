package com.ozu.cs394.cryptocoins.extension

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

import com.ozu.cs394.cryptocoins.R

fun ImageView.downloadFromUrl(coinUrl:String?){
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@downloadFromUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .error(R.mipmap.ic_launcher_round)
        .data(coinUrl)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}