package com.example.musicappdemo.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.musicappdemo.R
import com.example.musicappdemo.utils.Constant.NOTIFY_ID

@RequiresApi(Build.VERSION_CODES.O)
fun sendNotification(context: Context, title: String): Notification {
    val notificationChannel = NotificationChannel(
        NOTIFY_ID.toString(),
        context.getString(R.string.playing),
        NotificationManager.IMPORTANCE_DEFAULT
    )
    val serviceNotification =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    serviceNotification.createNotificationChannel(notificationChannel)
    val notificationBuilder = NotificationCompat.Builder(context, NOTIFY_ID.toString())
    return notificationBuilder
        .setSmallIcon(R.mipmap.ic_launcher_music_icon)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(Notification.CATEGORY_SERVICE)
        .setContentTitle(context.getString(R.string.playing))
        .setContentText(title)
        .build()
}
