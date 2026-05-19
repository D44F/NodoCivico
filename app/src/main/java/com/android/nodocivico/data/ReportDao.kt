package com.android.nodocivico.data

import androidx.room.*
import com.android.nodocivico.model.Report
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    
    @Query("SELECT * FROM reports ORDER BY time DESC")
    fun getAllReports(): Flow<List<Report>>

    @Query("SELECT * FROM reports WHERE id = :id")
    suspend fun getReportById(id: Int): Report?

    @Query("SELECT * FROM reports WHERE isSynced = 0")
    suspend fun getUnsyncedReports(): List<Report>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report): Long

    @Update
    suspend fun updateReport(report: Report): Int

    @Delete
    suspend fun deleteReport(report: Report)
}