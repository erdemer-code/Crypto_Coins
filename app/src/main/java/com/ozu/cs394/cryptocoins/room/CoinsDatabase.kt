package com.ozu.cs394.cryptocoins.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ozu.cs394.cryptocoins.model.response.CoinResponseModel

@Database(entities = [CoinResponseModel::class], version = 1, exportSchema = true)
abstract class CoinsDatabase: RoomDatabase() {
    abstract fun coinsDAO(): CoinsDAO

    companion object{
        @Volatile
        private var INSTANCE: CoinsDatabase? = null

        fun getInstance(context: Context): CoinsDatabase{
            if (INSTANCE == null){
                synchronized(CoinsDatabase::class){
                    INSTANCE = createDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun createDB(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CoinsDatabase::class.java, "coinsDB"
        )
            .fallbackToDestructiveMigrationFrom()
            .build()
    }
}