package com.miracozkan.yemekhanemenu.service

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.miracozkan.yemekhanemenu.util.sendNotification


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 30.10.2019 - 20:14          │
//└─────────────────────────────┘

class FMService : FirebaseMessagingService() {
    private val TAG = "FirebaseMesagingService"

    override fun onNewToken(token: String) {
        Log.e(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.notification?.let {
            Log.e(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!, it.title!!)
        }
    }

    private fun sendNotification(messageBody: String, messageTitle: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(messageTitle, messageBody, applicationContext)
    }
}