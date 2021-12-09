package com.example.medicationreminder

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MedicationDB.DOSE_UNIT_TABLE)
data class DoseUnit(val unit: String) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    fun toContentValues(): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(::id.name, id)
        contentValues.put(::unit.name, unit)
        return contentValues
    }
}
