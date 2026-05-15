package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.R

class SyncStatusFragment : Fragment(R.layout.fragment_sync_status) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val tvConnectionStatus = view.findViewById<TextView>(R.id.tvConnectionStatus)
        val tvSyncQueue = view.findViewById<TextView>(R.id.tvSyncQueue)
        val tvRetryInfo = view.findViewById<TextView>(R.id.tvRetryInfo)
        val tvPendingSync = view.findViewById<TextView>(R.id.tvPendingSync)
        val tvUploadedReports = view.findViewById<TextView>(R.id.tvUploadedReports)
        val btnSyncNow = view.findViewById<Button>(R.id.btnSyncNow)

        btnSyncNow.setOnClickListener {
            tvConnectionStatus.text = "Conexión disponible"
            tvSyncQueue.text = "No hay reportes pendientes"
            tvRetryInfo.text = "Sincronización simulada correctamente"
            tvPendingSync.text = "0 cambios en cola local"
            tvUploadedReports.text = "12 reportes sincronizados"

            Toast.makeText(
                requireContext(),
                "Sincronización simulada",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}