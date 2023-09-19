package br.com.epistemic.app.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.epistemic.app.data.service.NotificationService
import br.com.epistemic.app.data.service.NotificationService.Companion.REMINDER_CHANNEL_ID

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return

        if (message == REMINDER_CHANNEL_ID) {
            val notificationService = NotificationService(context)
            notificationService.showNotification()
        }
    }
}