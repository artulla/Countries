package com.graphql.country_api.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters

class MyNumberWorker(
    context: Context, params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "MyNumberWorker"
        private const val KEY_COUNT = "count"
        private const val CHANNEL_ID = "my_number_channel"
        private const val NOTIF_ID = 9871
    }

    override suspend fun doWork(): Result {
        // read current count (default 1)
        val count = inputData.getInt(KEY_COUNT, 1)

        // Make this worker foreground (optional but recommended for long runs)
        setForeground(createForegroundInfo("Printing number $count"))

        // Print/log the number (replace with whatever "print" you need)
        Log.d(TAG, "Number: $count")
        // If you also want to show a brief notification update:
        // updateNotification("Number: $count")

        // schedule next if not yet 60
        return if (count < 60) {
            scheduleNext(count + 1)
            Result.success()
        } else {
            Log.d(TAG, "Finished printing up to 60")
            Result.success()
        }
    }

    private fun scheduleNext(nextCount: Int) {
        val input = Data.Builder().putInt(KEY_COUNT, nextCount).build()

        /*val workRequest = OneTimeWorkRequestBuilder<MyNumberWorker>()
            .setInitialDelay(1, TimeUnit.SECONDS) // 1 minute delay
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST) // helps survive restrictions
            .setInputData(input)
            .build()*/

        // If you are using setExpedited, DO NOT use setInitialDelay
        val workRequest =
            OneTimeWorkRequestBuilder<MyNumberWorker>().setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST) // Or .DROP_WORK_REQUEST
                // .setInitialDelay(10, TimeUnit.MINUTES) // <-- REMOVE THIS LINE
                .setInputData(input).addTag("com.graphql.country_api.worker.MyNumberWorker").build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
                "MyNumberWorkerNextRun", ExistingWorkPolicy.REPLACE, workRequest
            )
    }

    private fun createForegroundInfo(contentText: String): ForegroundInfo {
        val channelId = ensureNotificationChannel()
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Numbers printer").setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // ✅ Ensure heads-up
            .setOngoing(true).build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(
                NOTIF_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            ForegroundInfo(NOTIF_ID, notification)
        }
    }


    private fun ensureNotificationChannel(): String {
        val nm =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Numbers printer",
                NotificationManager.IMPORTANCE_HIGH   // ✅ High importance
            ).apply {
                enableVibration(true)
                setShowBadge(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            nm.createNotificationChannel(channel)
        }
        return CHANNEL_ID
    }
}