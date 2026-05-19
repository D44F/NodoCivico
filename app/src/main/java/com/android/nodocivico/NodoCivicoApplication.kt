package com.android.nodocivico

import android.app.Application
import com.android.nodocivico.data.AppDatabase
import com.android.nodocivico.data.ReportRepository

class NodoCivicoApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ReportRepository(database.reportDao(), this) }
}