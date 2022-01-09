package com.ozu.cs394.cryptocoins.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object SharedPreferenceManager {
    lateinit var mPref: SharedPreferences

    fun init(context: Context){
        mPref = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
    }
    fun <T> putData(`object` :T, key:String){
        val jsonString = GsonBuilder().create().toJson(`object`)
        mPref.edit().putString(key,jsonString).apply()
    }
    inline fun<reified T> getData(key:String):T?{
        val data = mPref.getString(key,null)
        return GsonBuilder().create().fromJson(data,T::class.java)
    }
    fun removeSharedPreferences(){
        mPref.edit().clear().apply()
    }


}