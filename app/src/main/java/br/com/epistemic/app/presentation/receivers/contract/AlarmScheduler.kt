package br.com.epistemic.app.presentation.receivers.contract

import br.com.epistemic.app.presentation.receivers.model.AlarmItem

interface AlarmScheduler {
    fun schedule(item: AlarmItem): String?
    fun cancel(notifyPerDay: Int)
}