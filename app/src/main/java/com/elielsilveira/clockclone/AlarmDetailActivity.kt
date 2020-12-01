package com.elielsilveira.clockclone

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elielsilveira.clockclone.data.HandleAlarmsData
import com.elielsilveira.clockclone.data.model.Alarm
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_alarm_detail.*

class AlarmDetailActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_detail)

        val alarm: Alarm = intent.extras?.get("Alarm") as Alarm
        updateViewAlarm(alarm)

        buttonDelete.setOnClickListener {
            HandleAlarmsData(this).removeAlarm(alarm)
            onBackPressed()
        }

        buttonBack.setOnClickListener {
            onBackPressed()
        }

        switchActive.setOnCheckedChangeListener { _, isChecked ->
            alarm.active = isChecked
            updateAlarm(alarm)
        }

        checkboxVibrate.setOnCheckedChangeListener { _, isChecked ->
            alarm.vibrate = isChecked
            updateAlarm(alarm)
        }

        checkboxFriday.setOnCheckedChangeListener { _, isChecked ->
            alarm.weekDays.friday = isChecked

            updateAlarm(alarm)
        }
        checkboxWednesday.setOnCheckedChangeListener { _, isChecked ->
            alarm.weekDays.wednesday = isChecked
            updateAlarm(alarm)
        }
        checkboxTuesday.setOnCheckedChangeListener { _, isChecked ->
            alarm.weekDays.tuesday = isChecked
            updateAlarm(alarm)
        }
        checkboxThursday.setOnCheckedChangeListener { _, isChecked ->
            alarm.weekDays.thursday = isChecked
            updateAlarm(alarm)
        }
        checkboxSunday.setOnCheckedChangeListener { _, isChecked ->
            alarm.weekDays.sunday = isChecked
            updateAlarm(alarm)
        }
        checkboxMonday.setOnCheckedChangeListener { _, isChecked ->
            alarm.weekDays.monday = isChecked
            updateAlarm(alarm)
        }
        checkboxSaturday.setOnCheckedChangeListener { _, isChecked ->
            alarm.weekDays.saturday = isChecked
            updateAlarm(alarm)
        }

        hourWrapper.setOnClickListener {
            val timePickerDialog = MaterialTimePicker.newInstance()
            timePickerDialog.show(supportFragmentManager, "fragment_tag")
            timePickerDialog.setTimeFormat(TimeFormat.CLOCK_12H)
            timePickerDialog.hour = alarm.hour
            timePickerDialog.minute = alarm.minute

            timePickerDialog.setListener { dialog ->
                val hour = dialog.hour
                val minute = dialog.minute

                alarm.hour = hour
                alarm.minute = minute

                updateAlarm(alarm)
                updateViewAlarm(alarm)

            }
        }
    }

    private fun updateAlarm(alarm: Alarm) {
        HandleAlarmsData(this).updatedAlarm(alarm)
    }

    @SuppressLint("SetTextI18n")
    private fun updateViewAlarm(alarm: Alarm) {
        if (alarm.hour > 12) {
            textHour.text = (alarm.hour - 12).toString()
            textPeriod.text = "PM"
        } else {
            textHour.text = alarm.hour.toString()
            textPeriod.text = "AM"
        }
        textMinute.text = alarm.minute.toString()
        checkboxFriday.isChecked = alarm.weekDays.friday
        checkboxMonday.isChecked = alarm.weekDays.monday
        checkboxSaturday.isChecked = alarm.weekDays.saturday
        checkboxSunday.isChecked = alarm.weekDays.sunday
        checkboxThursday.isChecked = alarm.weekDays.thursday
        checkboxTuesday.isChecked = alarm.weekDays.tuesday
        checkboxWednesday.isChecked = alarm.weekDays.wednesday
        checkboxVibrate.isChecked = alarm.vibrate
        switchActive.isChecked = alarm.active
    }
}