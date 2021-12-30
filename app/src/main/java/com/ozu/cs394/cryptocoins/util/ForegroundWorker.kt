package com.ozu.cs394.cryptocoins.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.ozu.cs394.cryptocoins.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ForegroundWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {


    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        setForeground(createForegroundInfo())
        return@withContext runCatching {
            runTask()
            Result.success()
        }.getOrElse {
            Result.failure()
        }
    }


    private fun createForegroundInfo(): ForegroundInfo {
        val id = "1225"
        val channelName = "Downloads Notification"
        val title = "Downloading"
        val cancel = "Cancel"
        val body = "Long running task is running"

        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(id, channelName)
        }

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_stat_ic_notification)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()

        return ForegroundInfo(1, notification)
    }

    private suspend fun runTask() {
        delay(60000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(id: String, channelName: String) {
        val channel = NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        getSystemService(
            applicationContext,
            NotificationManager::class.java
        )?.createNotificationChannel(channel)
    }

}