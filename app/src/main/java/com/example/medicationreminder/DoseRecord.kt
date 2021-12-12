package com.example.medicationreminder

import kotlin.math.sign

data class DoseRecord(val doseTime: Long, val closestDose: Long = NONE): Comparable<DoseRecord> {

    companion object {
        const val NONE = -1L
        const val INVALID_TIME = -2L
    }

    override fun compareTo(other: DoseRecord): Int {
        return if (closestDose == other.closestDose)
            (other.doseTime - doseTime).sign
        else
            (other.closestDose - closestDose).sign
    }

    fun isAsNeeded(): Boolean {
        return closestDose == NONE
    }
}