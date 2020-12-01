package com.elielsilveira.clockclone.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.elielsilveira.clockclone.data.model.Alarm
import java.util.*

/**
 * Created by Eliel Silveira on 01/10/20.
 * ClockClone, com.elielsilveira.clockclone.manager.
 */
class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            startRescheduleAlarmsService(context)
        } else if (alarmIsToday(intent)) {
            startAlarmService(context)
        }
    }

    private fun alarmIsToday(intent: Intent): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today: Int = calendar.get(Calendar.DAY_OF_WEEK)
        val alarm: Alarm = intent.extras?.get("Alarm") as Alarm

        when (today) {
            Calendar.MONDAY -> {
                if (alarm.weekDays.monday) {
                    return true
                }
                return false
            }
            Calendar.TUESDAY -> {
                if (alarm.weekDays.tuesday) {
                    return true
                }
                return false
            }
            Calendar.WEDNESDAY -> {
                if (alarm.weekDays.wednesday) {
                    return true
                }
                return false
            }
            Calendar.THURSDAY -> {
                if (alarm.weekDays.thursday) {
                    return true
                }
                return false
            }
            Calendar.FRIDAY -> {
                if (alarm.weekDays.friday) {
                    return true
                }
                return false
            }
            Calendar.SATURDAY -> {
                if (alarm.weekDays.monday) {
                    return true
                }
                return false
            }
            Calendar.SUNDAY -> {
                if (alarm.weekDays.monday) {
                    return true
                }
                return false
            }
            else -> return false
        }
    }

    private fun startAlarmService(context: Context) {
        val intentService = Intent(context, AlarmService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }
}