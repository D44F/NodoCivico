package com.android.nodocivico.model

data class Report(
    val id: Int,
    val title: String,
    val category: String,
    val priority: String,
    val location: String,
    val time: String,
    val status: String
)