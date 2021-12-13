package com.ozu.cs394.cryptocoins.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ozu.cs394.cryptocoins.R
import com.ozu.cs394.cryptocoins.extension.downloadFromUrl

@BindingAdapter("android:showImage")
fun downloadImage(view:ImageView, url:String?){
    view.downloadFromUrl(url)
}

@BindingAdapter("android:checkTheSign")
fun checkValuePositiveOrNegative(view:TextView, value:String){
    if (value.replace(",",".").toDouble() > 0){
        view.setTextColor(view.resources.getColor(R.color.positive_green,null))
    } else {
        view.setTextColor(view.resources.getColor(R.color.negative_red,null))
    }
}