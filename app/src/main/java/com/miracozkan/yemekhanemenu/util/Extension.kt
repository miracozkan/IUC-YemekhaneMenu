package com.miracozkan.yemekhanemenu.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.View
import androidx.core.app.NotificationCompat
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.ui.activity.SplashActivity


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 07.10.2019 - 21:11          │
//└─────────────────────────────┘

private val NOTIFICATION_ID = 0

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun String?.printMenu(): String {
    this?.let {
        return it
    }
    return "MENU MEVCUT DEGIL"
}

fun NotificationManager.sendNotification(
    messageTitle: String,
    messageBody: String,
    applicationContext: Context
) {

    val contentIntent = Intent(applicationContext, SplashActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notifyImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.ic_event
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_event)
        .setContentTitle(messageTitle)
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setLargeIcon(notifyImage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())
}