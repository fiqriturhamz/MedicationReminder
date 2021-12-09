package com.example.medicationreminder

import androidx.room.Entity
import java.io.File

@Entity(tableName = MedicationDB.IMAGE_TABLE, primaryKeys = ["medId", "doseTime"])
data class ProofImage(
    val medId: Long,
    val doseTime: Long,
    val filePath: String,
) {
    fun deleteImageFile(storageDir: File) {
        val imageFile = File(storageDir.absolutePath + filePath)

        if (imageFile.exists() && imageFile.canWrite()) {
            imageFile.delete()
        }
    }
}
