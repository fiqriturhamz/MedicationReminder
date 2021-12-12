package com.example.medicationreminder

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProofImageDao {

    @Insert
    fun insertAll(vararg proofImages: ProofImage)

    @Update
    fun updateProofImages(vararg proofImages: ProofImage)

    @Delete
    fun delete(proofImage: ProofImage)

    @Query("SELECT * FROM ${MedicationDB.IMAGE_TABLE} WHERE medId = :medId AND doseTime = :doseTime LIMIT 1")
    fun get(medId: Long, doseTime: Long): ProofImage

    @Query("SELECT * FROM ${MedicationDB.IMAGE_TABLE}")
    fun getAll(): LiveData<MutableList<ProofImage>>

    @Query("SELECT * FROM ${MedicationDB.IMAGE_TABLE}")
    fun getAllRaw(): MutableList<ProofImage>

    @Query("SELECT * FROM ${MedicationDB.IMAGE_TABLE} WHERE medId = :medId")
    fun getProofImagesByMedId(medId: Long): MutableList<ProofImage>

    @Query("SELECT EXISTS(SELECT * FROM ${MedicationDB.IMAGE_TABLE} WHERE medId = :medId AND doseTime = :doseTime)")
    fun proofImageExists(medId: Long, doseTime: Long): Boolean

}