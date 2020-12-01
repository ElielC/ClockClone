package com.elielsilveira.clockclone.manager

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.elielsilveira.clockclone.R
import com.elielsilveira.clockclone.RingActivity
import com.elielsilveira.clockclone.data.model.Alarm
import kotlin.time.measureTimedValue

/**
 * Created by Eliel Silveira on 01/10/20.
 * ClockClone, com.elielsilveira.clockclone.manager.
 */
class AlarmService : Service() {
    private var mediaPlayer = MediaPlayer()
    private lateinit var vibrator: Vibrator

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.)
        mediaPlayer.isLooping = true

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val alarm: Alarm = intent.extras?.get("Alarm") as Alarm
        val notificationIntent = Intent(this, RingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, "ALARM_SERVICE_CHANNEL")
            .setContentTitle("${alarm.hour}:${alarm.minute}")
            .setContentText("Ring Ring .. Ring Ring")
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentIntent(pendingIntent)
            .build()

        mediaPlayer.start()

        val pattern = longArrayOf(0, 100, 1000)
        vibrator.vibrate(pattern, 0)

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.stop()
        vibrator.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}