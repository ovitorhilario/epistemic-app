package br.com.epistemic.app.presentation.receivers.impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.epistemic.app.R
import br.com.epistemic.app.data.service.NotificationService.Companion.REMINDER_CHANNEL_ID
import br.com.epistemic.app.presentation.receivers.AlarmReceiver
import br.com.epistemic.app.presentation.receivers.contract.AlarmScheduler
import br.com.epistemic.app.presentation.receivers.model.AlarmItem
import java.util.Calendar
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmItem): String? {
        if (item.notifyPerDay == 0) return null
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", REMINDER_CHANNEL_ID)
        }
        val notifyHours = getNotifyHours(item.notifyPerDay)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        for ((idx, hour) in notifyHours.withIndex()) {
            val requestCode = idx + 1
            val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)

            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        return context.getString(R.string.schedules) + notifyHours.joinToString(separator = " - ") { "${it}h" }
    }

    override fun cancel(notifyPerDay: Int) {
        if (notifyPerDay == 0) return
        try {
            for (idx in 1..notifyPerDay) {
                alarmManager.cancel(
                    PendingIntent.getBroadcast(
                        context, idx, Intent(context, AlarmReceiver::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    companion object {
        const val TAG = "AlarmScheduler"
    }
}

/**
 * Função que retorna os horários dos alarmes
 * Com base na quantidade de notificações por dia
*/
private fun getNotifyHours(notifyPerDay: Int): List<Int> {
    val totalHours = 24 - 6 // 24 horas - (0h00 até 6h00 - 6 horas)
    val targetHour = 24 // Limite >> 0h00
    val initHour = 6 // Início >> 6h00

    // Cálculo do intervalo entre cada notificação
    val div = Math.floorDiv(totalHours, notifyPerDay + 1)
    // Lista para armazenar os horários de notificação
    val notifyHours = mutableListOf<Int>()

    /*
        Este laço for adiciona os horários à lista. A expressão:
        `hour in (initHour + div)..targetHour step div`
        cria um range de elementos com um intervalo definido por `div`.

        ex: início: 2, final: 10, div/step: 2, range: 2, 4, 6, 8, 10
     */
    for (hour in (initHour + div)..targetHour step div) {
        // Se a quantidade de notificações é menor que a definida, outro é adicionado
        if (notifyHours.size < notifyPerDay) {
            notifyHours.add(hour)
        } else break // Se a quantidade for sufiente, o `for` é finalizado
    }

    return notifyHours.toList()
}
