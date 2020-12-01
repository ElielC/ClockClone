package com.elielsilveira.clockclone

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elielsilveira.clockclone.data.HandleAlarmsData
import com.elielsilveira.clockclone.data.model.Alarm
import com.elielsilveira.clockclone.data.model.WeekDays
import com.elielsilveira.clockclone.presentation.AlarmsAdapter
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Integer.parseInt


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadAlarms()

        newAlarm.setOnClickListener {
            val timePickerDialog = MaterialTimePicker.newInstance()
            timePickerDialog.show(supportFragmentManager, "fragment_tag")
            timePickerDialog.setTimeFormat(TimeFormat.CLOCK_12H)

            timePickerDialog.setListener { dialog ->
                val hour = dialog.hour
                val minute = dialog.minute

                val alarms: MutableList<Alarm> = mutableListOf<Alarm>()
                HandleAlarmsData(this).getAlarms()?.let { it1 -> alarms.addAll(it1) }
                alarms.add(
                    Alarm(
                        id = java.util.UUID.randomUUID().toString(),
                        hour = hour,
                        minute = minute,
                        weekDays = WeekDays(
                            monday = false,
                            wednesday = false,
                            tuesday = false,
                            thursday = false,
                            friday = false,
                            saturday = false,
                            sunday = false
                        ),
                        active = true,
                        vibrate = true
                    )
                )

                HandleAlarmsData(this).setAlarms(alarms)

                loadAlarms()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadAlarms()
    }

    private fun loadAlarms() {
        val viewModel by viewModels<AlarmsViewModel>()

        viewModel.getAlarms(this)

        viewModel.alarmsLiveData.observe(this, Observer {
            it?.let { alarms ->
                with(recyclerAlarms) {
                    layoutManager =
                        LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = AlarmsAdapter(alarms) { alarm ->
                        val intent =
                            Intent(this@MainActivity, AlarmDetailActivity()::class.java)
                        intent.putExtra("Alarm", alarm)

                        startActivity(intent)
                    }
                }
            }
        })
    }
}