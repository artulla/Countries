package com.graphql.country_api.worker

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.graphql.country_api.data.local.room_db.AppDatabase
import kotlinx.coroutines.delay

class SyncWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
//        setForeground(createForegroundInfo())

       /* val dao = AppDatabase.getInstance(context).customerDao()
        val unsynced = dao.getUnsyncedCustomers()

        if (unsynced.isNotEmpty()) {
            // Simulate API call
            delay(3000) // 3 sec delay for network call

            val ids = unsynced.map { it.id }
            dao.markAsSynced(ids)
        }*/

        return Result.success()
    }

 /*   private fun createForegroundInfo(): ForegroundInfo {
        val channelId = "sync_channel"
        val title = "Syncing Data"
        "Cancel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Sync", NotificationManager.IMPORTANCE_LOW)
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }

       val notification = NotificationCompat.Builder(context, channelId).setContentTitle(title)
            .setContentText("Uploading unsynced data...")
            .setSmallIcon(R.drawable.ic_forground_notification)
            .build()

        return ForegroundInfo(1, notification)
    }*/
}
