package com.greetingwhatsapp

import android.app.Notification

import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.d("Notification", "onNotificationPosted started")
        Log.d("Notification", "onNotificationPosted $sbn")
        Log.d("Notification", "onNotificationPosted packageName: ${sbn.packageName}")
        val title = sbn.notification.extras.getString("android.title")
        Log.d("Notification", "onNotificationPosted title: $title")
        val text = sbn.notification?.extras?.getString("android.text")
        Log.d(
            "Notification",
            "onNotificationPosted text: $text"
        )
        text?.let {
            if (it.contains(LocalManager.textInclude) && checkIfContainsInList(title)) {
                Log.d("Notification", "LocalManager.contactName: ${LocalManager.contactName}")
                val quickReplyAction = getQuickReplayAction(sbn.notification)
                val remoteInputs = quickReplyAction?.remoteInputs
                val pendingIntent = quickReplyAction?.actionIntent
                val intent = Intent()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val bundle = Bundle()
                for (remoteIn in remoteInputs!!){
                    bundle.putCharSequence(remoteIn.resultKey, LocalManager.greeting)
                }

                RemoteInput.addResultsToIntent(remoteInputs, intent, bundle)
                try {
                    pendingIntent?.send(MyApp.appContext, 0, intent)
                }catch (ex: Exception){

                }
            }

        }
    }

    private fun checkIfContainsInList(title: String?): Boolean {
        LocalManager.contactName.forEach { item ->
            title?.let { contactName ->
                if (contactName.contains(item)){
                    return true
                }
            }
        }
        return false
    }

    private fun getQuickReplayAction(notification: Notification): NotificationCompat.Action? {
        try {
            for (i in 0..NotificationCompat.getActionCount(notification)) {
                val action = NotificationCompat.getAction(notification, i)
                for (x in 0..action?.remoteInputs?.size!!){
                    val remoteInput = action.remoteInputs!![x]
                    if (remoteInput.resultKey.lowercase().contains("reply"))
                        return action
                }
            }
        }catch (e: Exception){
            Log.d("Notification", "Exception: ${e.message}")
        }
        return null
    }
}