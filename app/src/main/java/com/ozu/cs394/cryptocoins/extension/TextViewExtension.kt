package com.ozu.cs394.cryptocoins.extension

import android.widget.TextView
import com.ozu.cs394.cryptocoins.R

fun TextView.checkValuePositiveOrNegative(value:String){
    this.text = value + "%"
    if (value.replace(",",".").toDouble().toDouble() > 0){
        this.setTextColor(this.resources.getColor(R.color.positive_green,null))
    } else {
        this.setTextColor(this.resources.getColor(R.color.negative_red,null))
    }
}