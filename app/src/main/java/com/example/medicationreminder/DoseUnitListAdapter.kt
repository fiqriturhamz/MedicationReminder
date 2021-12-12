package com.example.medicationreminder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.google.android.material.textview.MaterialTextView
import java.util.*

class DoseUnitListAdapter(
    private val context: Context,
    private val doseUnits: MutableList<DoseUnit>,
) : BaseAdapter(),
    Filterable {
    var filteredUnits = doseUnits

    override fun getCount(): Int {
        return filteredUnits.size
    }

    override fun getItem(position: Int): DoseUnit {
        return filteredUnits[position]
    }

    override fun getItemId(position: Int): Long {
        return filteredUnits[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.dose_unit_list_item, parent, false)

        val unitNameLabel = view?.findViewById<MaterialTextView>(R.id.unit_name_label)
        unitNameLabel?.text = getItem(position).unit

        return view!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.toLowerCase(Locale.getDefault())

                val results = FilterResults()
                results.values = if (query == null || query.isEmpty()) {
                    doseUnits
                } else {
                    doseUnits.filter { doseUnit ->
                        query in doseUnit.unit.toLowerCase(Locale.getDefault())
                    }
                }

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredUnits = results?.values as MutableList<DoseUnit>
                notifyDataSetChanged()
            }
        }
    }


}