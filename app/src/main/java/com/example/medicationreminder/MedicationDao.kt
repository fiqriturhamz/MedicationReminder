package com.example.medicationreminder

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.medicationreminder.MedicationDB.Companion.MED_TABLE

@Dao
interface MedicationDao {
    @Insert
    fun insertAll(vararg medications: Medication)

    @Update
    fun updateMedications(vararg medications: Medication)

    @Delete
    fun delete(medication: Medication)

    @Query("SELECT * FROM $MED_TABLE WHERE id = :medId LIMIT 1")
    fun get(medId: Long): Medication

    @Query("SELECT * FROM $MED_TABLE")
    fun getAll(): LiveData<MutableList<Medication>>

    @Query("SELECT * FROM $MED_TABLE")
    fun getAllRaw(): MutableList<Medication>

    @Query("SELECT EXISTS(SELECT * FROM $MED_TABLE WHERE id = :medId)")
    fun medicationExists(medId: Long): Boolean
}