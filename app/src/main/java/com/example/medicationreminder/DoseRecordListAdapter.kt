package com.example.medicationreminder

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.android.material.textview.MaterialTextView

class DoseRecordListAdapter(private val context: Context, private val doseRecordList: MutableList<DoseRecord>) : BaseAdapter(){

    override fun getCount(): Int {
        return doseRecordList.size
    }

    override fun getItem(position: Int): Any {
        return doseRecordList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.dose_list_item, parent, false)
        val doseTakenTimeLabel = view?.findViewById<MaterialTextView>(R.id.dose_taken_time_label)
        val closestTimeLabel = view?.findViewById<MaterialTextView>(R.id.closest_dose_time_label)

        val yesterdayString = context.getString(R.string.yesterday)
        val todayString = context.getString(R.string.today)
        val tomorrowString = context.getString(R.string.tomorrow)
        val dateFormat = context.getString(R.string.date_format)

        val systemIs24Hour = DateFormat.is24HourFormat(context)

        val timeFormat = if (systemIs24Hour) {
            context.getString(R.string.time_24)
        }
        else {
            context.getString(R.string.time_12)
        }

        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0]
        } else {
            Resources.getSystem().configuration.locale
        }

        val doseTimeString = Medication.doseString(
            yesterdayString,
            todayString,
            tomorrowString,
            doseRecordList[position].doseTime,
            dateFormat,
            timeFormat,
            locale
        )

        doseTakenTimeLabel?.text = context.getString(R.string.time_taken, doseTimeString)

        if(doseRecordList[position].isAsNeeded()) {
            closestTimeLabel?.visibility = View.GONE
        }
        else {
            closestTimeLabel?.visibility = View.VISIBLE
            closestTimeLabel?.text = context.getString(
                R.string.closest_dose_label,
                Medication.doseString(
                    yesterdayString,
                    todayString,
                    tomorrowString,
                    doseRecordList[position].closestDose,
                    dateFormat,
                    timeFormat,
                    locale
                )
            )
        }

        return view!!
    }
}