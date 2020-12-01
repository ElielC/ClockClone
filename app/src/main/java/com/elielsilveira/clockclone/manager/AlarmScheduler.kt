package com.elielsilveira.clockclone.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.elielsilveira.clockclone.data.model.Alarm
import java.lang.Integer.parseInt
import java.util.*

/**
 * Created by Eliel Silveira on 01/10/20.
 * ClockClone, com.elielsilveira.clockclone.manager.
 */
class AlarmScheduler(private val context: Context, private val alarm: Alarm) {
    fun makeSchedule() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra("ALARM", alarm)

        val alarmPendingIntent = PendingIntent.getBroadcast(context, parseInt(alarm.id), intent, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, alarm.hour)
        calendar.set(Calendar.MINUTE, alarm.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }

        val runDaily: Long = 24 * 60 * 60 * 1000
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            runDaily,
            alarmPendingIntent
        )
    }

}