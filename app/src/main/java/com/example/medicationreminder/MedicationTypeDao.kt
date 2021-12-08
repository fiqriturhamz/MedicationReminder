package com.example.medicationreminder

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.medicationreminder.MedicationDB.Companion.MED_TYPE_TABLE

@Dao
interface MedicationTypeDao {

    @Insert
    fun insertAll(vararg medicationType: MedicationType)

    @Delete
    fun delete(medicationType: MedicationType)

    @Query("SELECT * FROM $MED_TYPE_TABLE WHERE id = :typeId LIMIT 1")
    fun get(typeId: Long): MedicationType

    @Query("SELECT * FROM $MED_TYPE_TABLE WHERE name = :typeName LIMIT 1")
    fun get(typeName: String): MedicationType

    @Query("SELECT * FROM $MED_TYPE_TABLE")
    fun getAll(): LiveData<MutableList<MedicationType>>

    @Query("SELECT * FROM $MED_TYPE_TABLE")
    fun getAllRaw(): MutableList<MedicationType>

    @Query("SELECT EXISTS(SELECT * FROM $MED_TYPE_TABLE WHERE id = :typeId)")
    fun typeExists(typeId: Long): Boolean

    @Query("SELECT EXISTS(SELECT * FROM $MED_TYPE_TABLE WHERE name = :typeName)")
    fun typeExists(typeName: String): Boolean
}