package com.example.medicationreminder

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.medicationreminder.MedicationDB.Companion.DOSE_UNIT_TABLE

@Dao
interface DoseUnitDao {

    @Insert
    fun insertAll(vararg doseUnit: DoseUnit)

    @Delete
    fun delete(doseUnit: DoseUnit)

    @Query("SELECT * FROM $DOSE_UNIT_TABLE WHERE id = :unitId LIMIT 1")
    fun get(unitId: Long): DoseUnit

    @Query("SELECT * FROM $DOSE_UNIT_TABLE WHERE unit = :unitName LIMIT 1")
    fun get(unitName: String): DoseUnit

    @Query("SELECT * FROM $DOSE_UNIT_TABLE")
    fun getAll(): LiveData<MutableList<DoseUnit>>

    @Query("SELECT * FROM $DOSE_UNIT_TABLE")
    fun getAllRaw(): MutableList<DoseUnit>

    @Query("SELECT EXISTS(SELECT * FROM $DOSE_UNIT_TABLE WHERE id = :unitId)")
    fun unitExists(unitId: Long): Boolean

    @Query("SELECT EXISTS(SELECT * FROM $DOSE_UNIT_TABLE WHERE unit = :unitName)")
    fun unitExists(unitName: String): Boolean
}