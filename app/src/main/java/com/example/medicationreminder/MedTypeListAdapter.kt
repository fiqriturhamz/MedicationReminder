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

class MedTypeListAdapter(private val context: Context, private val medicationTypes: MutableList<MedicationType>) : BaseAdapter(),
    Filterable {
    var filteredTypes = medicationTypes

    override fun getCount(): Int {
        return filteredTypes.size
    }

    override fun getItem(position: Int): MedicationType {
        return filteredTypes[position]
    }

    override fun getItemId(position: Int): Long {
        return filteredTypes[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.med_type_list_item, parent, false)

        val nameLabel = view?.findViewById<MaterialTextView>(R.id.type_name_label)
        nameLabel?.text = getItem(position).name

        return view!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.toLowerCase(Locale.getDefault())

                val results = FilterResults()
                results.values = if (query == null || query.isEmpty()) {
                    medicationTypes
                }
                else {
                    medicationTypes.filter { type ->
                        query in type.name.toLowerCase(Locale.getDefault())
                    }
                }

                return results
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredTypes = results?.values as MutableList<MedicationType>
                notifyDataSetChanged()
            }

        }
    }
}