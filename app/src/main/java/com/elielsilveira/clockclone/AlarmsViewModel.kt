package com.elielsilveira.clockclone

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elielsilveira.clockclone.data.HandleAlarmsData
import com.elielsilveira.clockclone.data.model.Alarm

class AlarmsViewModel() : ViewModel() {
    val alarmsLiveData: MutableLiveData<List<Alarm>> = MutableLiveData()

    fun getAlarms(context: Context) {
        alarmsLiveData.value = HandleAlarmsData(context).getAlarms()
    }

}