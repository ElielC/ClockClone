package com.elielsilveira.clockclone.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.elielsilveira.clockclone.data.model.Alarm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HandleAlarmsData(val context: Context) {
    private val appSettingsPrefs: SharedPreferences =
        context.getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE)
    private val sharedPrefsEdit: SharedPreferences.Editor = appSettingsPrefs.edit()

    private val gson: Gson = Gson()
    fun setAlarms(list: MutableList<Alarm>) {
        val alarmsJson: String = gson.toJson(list)
        sharedPrefsEdit.putString("Alarms", alarmsJson).apply()
    }

    fun getAlarms(): List<Alarm>? {
        val alarmJson: String? = appSettingsPrefs.getString("Alarms", null)

        if (alarmJson != null) {
            val alarms: List<Alarm>
            val type = object : TypeToken<List<Alarm>>() {}.type
            alarms = gson.fromJson(alarmJson, type)
            return alarms
        }
        return null
    }

    fun updatedAlarm(alarm: Alarm) {
        val alarms: MutableList<Alarm> = mutableListOf<Alarm>()
        getAlarms()?.let { it -> alarms.addAll(it) }
        val index = alarms.indexOfFirst { it.id == alarm.id }
        alarms[index] = alarm

        setAlarms(alarms)
    }

    fun removeAlarm(alarm: Alarm) {
        val alarms: MutableList<Alarm> = mutableListOf<Alarm>()
        getAlarms()?.let { it -> alarms.addAll(it) }
        val updatedAlarms: List<Alarm> = alarms.minus(alarm)

        setAlarms(updatedAlarms as MutableList<Alarm>)
    }


}