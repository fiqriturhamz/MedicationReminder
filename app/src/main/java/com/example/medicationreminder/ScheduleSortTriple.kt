package com.example.medicationreminder

import kotlin.math.sign

data class ScheduleSortTriple(
    val timeInMillis: Long,
    val schedule: RepeatSchedule,
    val scheduleIndex: Int)
    : Comparable<ScheduleSortTriple> {
    override fun compareTo(other: ScheduleSortTriple): Int {
        return (this.timeInMillis - other.timeInMillis).sign
    }
}
