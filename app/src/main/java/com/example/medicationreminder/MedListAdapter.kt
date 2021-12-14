package com.example.medicationreminder


import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.android.material.textview.MaterialTextView
import java.util.*

class MedListAdapter(private val context: Context, private val medications: MutableList<Medication>, private val medicationTypes: MutableList<MedicationType>) : BaseAdapter() {
    private val isSystem24Hour = DateFormat.is24HourFormat(context)
    private val calendar = Calendar.getInstance()

    companion object {
        private const val INACTIVE_VIEW_ALPHA = 0.70F
    }

    override fun getCount(): Int {
        return medications.size
    }

    override fun getItem(position: Int): Medication {
        return medications[position]
    }

    override fun getItemId(position: Int): Long {
        return medications[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.med_list_item, parent, false)
        val nameLabel = view?.findViewById<MaterialTextView>(R.id.name_label)
        nameLabel?.text = medications[position].name
        val typeLabel = view?.findViewById<MaterialTextView>(R.id.type_label)
        val timeLabel = view?.findViewById<MaterialTextView>(R.id.time_label)
        val takenLabel = view?.findViewById<MaterialTextView>(R.id.taken_label)

        val typeName = medicationTypes.find { type ->
            type.id == medications[position].typeId
        }?.name

        typeName?.apply {
            typeLabel?.text = context.getString(R.string.type_label_format, typeName)
        }

        calendar.timeInMillis = medications[position].calculateClosestDose().timeInMillis
        if (medications[position].active) {
            if (medications[position].isAsNeeded()) {
                takenLabel?.visibility = View.GONE
                timeLabel?.text = context.getString(R.string.taken_as_needed)
            } else {
                takenLabel?.visibility = View.VISIBLE
                timeLabel?.text = if (isSystem24Hour) DateFormat.format(
                    context.getString(R.string.time_24),
                    calendar
                )
                else DateFormat.format(context.getString(R.string.time_12), calendar)
                if (medications[position].closestDoseAlreadyTaken()) {
                    takenLabel?.text = context.getString(R.string.taken)
                } else {
                    takenLabel?.text = context.getString(R.string.not_taken)
                }
            }
        }
        else {
            takenLabel?.visibility = View.GONE
            timeLabel?.text = context.getString(R.string.inactive)
            view?.alpha = INACTIVE_VIEW_ALPHA
        }

        return view!!
    }
}