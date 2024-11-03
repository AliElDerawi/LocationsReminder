package com.udacity.project4.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import com.google.android.gms.location.GeofenceStatusCodes
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.locationreminders.ReminderDescriptionActivity
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem


object NotificationUtils {

    private const val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + ".channel"

    fun sendNotification(context: Context, reminderDataItem: ReminderDataItem) {

        val notificationManager = context.notificationManager

        val intent =
            context.createIntent<ReminderDescriptionActivity>(ReminderDescriptionActivity.EXTRA_ReminderDataItem to reminderDataItem)


        //create a pending intent that opens ReminderDescriptionActivity when the user clicks on the notification
        val stackBuilder = TaskStackBuilder.create(context)
            .addParentStack(ReminderDescriptionActivity::class.java)
            .addNextIntent(intent)

        var notificationPendingIntent: PendingIntent? = null

        val isSupportsAndroidM = AppSharedMethods.isSupportsAndroidM {
            notificationPendingIntent =
                stackBuilder
                    .getPendingIntent(getUniqueId(), PendingIntent.FLAG_MUTABLE)
        }

        if (!isSupportsAndroidM) {
            notificationPendingIntent =
                stackBuilder
                    .getPendingIntent(getUniqueId(), PendingIntent.FLAG_UPDATE_CURRENT)
        }


//    build the notification object with the data to be shown
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(reminderDataItem.title)
            .setContentText(reminderDataItem.location)
            .setContentIntent(notificationPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager!!.notify(getUniqueId(), notification)
    }

    fun createChannel(context: Context) {
        AppSharedMethods.isSupportsOreo {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.channel_name),

                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description =
                context.getString(R.string.notification_channel_description)

            val notificationManager = context.getSystemService<NotificationManager>()



            notificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

    fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())

    fun errorMessage(context: Context, errorCode: Int): String {
        val resources = context.resources
        return when (errorCode) {
            GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> resources.getString(
                R.string.geofence_not_available
            )

            GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> resources.getString(
                R.string.geofence_too_many_geofences
            )

            GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> resources.getString(
                R.string.geofence_too_many_pending_intents
            )

            else -> resources.getString(R.string.unknown_geofence_error)
        }
    }


    fun sendNotificationAboutEnteredGeofence(
        context: Context,
        reminderDataItem: ReminderDataItem
    ) {

        val notificationManager = context.notificationManager


        val intent =
            context.createIntent<ReminderDescriptionActivity>(ReminderDescriptionActivity.EXTRA_ReminderDataItem to reminderDataItem)

        //create a pending intent that opens ReminderDescriptionActivity when the user clicks on the notification
        val stackBuilder = TaskStackBuilder.create(context)
            .addParentStack(ReminderDescriptionActivity::class.java)
            .addNextIntent(intent)
        var notificationPendingIntent: PendingIntent? = null

        val isSupportsAndroidM = AppSharedMethods.isSupportsAndroidM {
            notificationPendingIntent =
                stackBuilder
                    .getPendingIntent(getUniqueId(), PendingIntent.FLAG_MUTABLE)
        }

        if (!isSupportsAndroidM)
            stackBuilder
                .getPendingIntent(getUniqueId(), PendingIntent.FLAG_UPDATE_CURRENT)


//    build the notification object with the data to be shown
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(
                TextUtils.concat(
                    context.getString(R.string.text_msg_entered_geofence),
                    " ",
                    reminderDataItem.title
                )
            )
            .setContentText(reminderDataItem.location)
            .setContentIntent(notificationPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager!!.notify(getUniqueId(), notification)
    }
}

