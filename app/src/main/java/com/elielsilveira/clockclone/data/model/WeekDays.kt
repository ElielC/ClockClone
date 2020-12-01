package com.elielsilveira.clockclone.data.model

import java.io.Serializable

data class WeekDays(
    var monday: Boolean,
    var tuesday: Boolean,
    var wednesday: Boolean,
    var thursday: Boolean,
    var friday: Boolean,
    var saturday: Boolean,
    var sunday: Boolean,
) : Serializable