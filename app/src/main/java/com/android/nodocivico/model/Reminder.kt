package com.android.nodocivico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val reportId: Int,
    val title: String,
    val dateTime: Long, // Timestamp
    val isDone: Boolean = false
)