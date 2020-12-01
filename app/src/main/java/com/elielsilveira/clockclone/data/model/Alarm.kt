package com.elielsilveira.clockclone.data.model

import java.io.Serializable

data class Alarm(
    var id: String,
    var hour: Int,
    var minute: Int,
    var weekDays: WeekDays,
    var active: Boolean,
    var vibrate: Boolean,
) : Serializable