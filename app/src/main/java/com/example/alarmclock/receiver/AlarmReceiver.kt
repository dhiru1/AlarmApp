package com.example.alarmclock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.alarmclock.notification.NotificationUtils

class AlarmReceiver : BroadcastReceiver() {
    //lateinit var mediaPlayer:MediaPlayer
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
       /*mediaPlayer= MediaPlayer.create(context, R.raw.alarm)
        mediaPlayer.setLooping(true)*/
        val notificationUtils =
            NotificationUtils(context)
        val notification = notificationUtils.getNotificationBuilder().build()
        notificationUtils.getManager().notify(0, notification)

    }
}