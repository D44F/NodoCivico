package com.android.nodocivico.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("category")
    val category: String,
    
    @SerializedName("priority")
    val priority: String,
    
    @SerializedName("location")
    val location: String,
    
    @SerializedName("time")
    val time: String,
    
    @SerializedName("status")
    val status: String,
    
    @SerializedName("isSynced")
    val isSynced: Boolean = false
)