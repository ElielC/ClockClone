package com.elielsilveira.clockclone.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elielsilveira.clockclone.R
import com.elielsilveira.clockclone.data.model.Alarm
import kotlinx.android.synthetic.main.item_alarm.view.*

class AlarmsAdapter(
    private val alarms: List<Alarm>,
    private val onItemClickListener: ((alarm: Alarm) -> Unit)
) : RecyclerView.Adapter<AlarmsAdapter.AlarmsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmsAdapter.AlarmsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmsViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount() = alarms.count()

    override fun onBindViewHolder(viewHolder: AlarmsViewHolder, position: Int) {
        viewHolder.bindView(alarms[position])
    }

    class AlarmsViewHolder(
        itemView: View,
        private val onItemClickListener: (alarm: Alarm) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val hour = itemView.textHour
        private val minute = itemView.textMinute
        private val period = itemView.textPeriod

        @SuppressLint("SetTextI18n")
        fun bindView(alarm: Alarm) {
            if (alarm.hour > 12) {
                hour.text = ((alarm.hour - 12).toString())
                period.text = "PM"
            } else {
                hour.text = alarm.hour.toString()
                period.text = "AM"
            }
            minute.text = alarm.minute.toString()

            itemView.setOnClickListener {
                onItemClickListener.invoke(alarm)
            }
        }
    }
}