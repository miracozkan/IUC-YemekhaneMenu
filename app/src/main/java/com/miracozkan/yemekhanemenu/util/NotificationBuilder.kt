package com.miracozkan.yemekhanemenu.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.11.2019 - 20:37          │
//└─────────────────────────────┘

class NotificationBuilder {
    fun createChannel(channelId: String, channelName: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(false)
            }
            notificationChannel.apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Aylık Yemek Menüsü Bildirimi"
            }
            val notificationManager = getSystemService(context, NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
    }
}