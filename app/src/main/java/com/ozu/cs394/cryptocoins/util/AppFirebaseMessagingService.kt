package com.ozu.cs394.cryptocoins.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ozu.cs394.cryptocoins.R
import android.provider.Settings
import android.widget.RemoteViews
import java.io.IOException

import android.graphics.BitmapFactory

import android.graphics.Bitmap

import java.io.InputStream

import java.net.HttpURLConnection

import java.net.URL





class AppFirebaseMessagingService: FirebaseMessagingService() {
    private val channelId = "CHANNEL_ID"
    private lateinit var notificationLayout: RemoteViews

    companion object{
        const val TAG = "AppFirebaseMessagingService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "Refreshed token: $token")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(p0: RemoteMessage) {

        notificationLayout = RemoteViews(this.packageName,R.layout.notification_design)

        val title = p0.notification?.title
        val body = p0.notification?.body
        val image = p0.notification?.imageUrl

        Log.e("ImageUri",image.toString())


        notificationLayout.setTextViewText(R.id.tvNotifTitle,title)
        notificationLayout.setTextViewText(R.id.tvNotifBody,body)
        notificationLayout.setImageViewBitmap(R.id.ivNotifCoin,getBitmapFromURL(image.toString()))


        val channel = NotificationChannel(
            channelId,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = Notification.Builder(this,channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_stat_ic_notification)
            .setCustomContentView(notificationLayout)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)

        NotificationManagerCompat.from(this).notify(1,notification.build())
        super.onMessageReceived(p0)
    }


    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)
            bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}