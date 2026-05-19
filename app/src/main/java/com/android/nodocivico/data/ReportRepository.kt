package com.android.nodocivico.data

import android.content.Context
import com.android.nodocivico.model.Report
import com.android.nodocivico.network.RetrofitClient
import kotlinx.coroutines.flow.Flow

class ReportRepository(private val reportDao: ReportDao, private val context: Context) {

    val allReports: Flow<List<Report>> = reportDao.getAllReports()

    suspend fun insert(report: Report) {
        val id = reportDao.insertReport(report)
        syncReport(report.copy(id = id.toInt()))
    }

    private suspend fun syncReport(report: Report) {
        try {
            val response = RetrofitClient.getInstance(context).createReport(report)
            if (response.isSuccessful) {
                reportDao.updateReport(report.copy(isSynced = true))
            } else if (response.code() == 400 || response.code() == 409) {
                val updateRes = RetrofitClient.getInstance(context).updateReport(report.id, report)
                if (updateRes.isSuccessful) {
                    reportDao.updateReport(report.copy(isSynced = true))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun syncPendingReports() {
        reportDao.getUnsyncedReports().forEach { syncReport(it) }
    }

    suspend fun update(report: Report) {
        reportDao.updateReport(report)
        syncReport(report)
    }

    suspend fun delete(report: Report) = reportDao.deleteReport(report)
    suspend fun getReportById(id: Int) = reportDao.getReportById(id)
    suspend fun getUnsyncedReports() = reportDao.getUnsyncedReports()
}