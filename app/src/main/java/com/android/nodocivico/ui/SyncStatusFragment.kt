package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.NodoCivicoApplication
import com.android.nodocivico.R
import com.android.nodocivico.viewmodel.ReportViewModel
import com.android.nodocivico.viewmodel.ReportViewModelFactory

class SyncStatusFragment : Fragment(R.layout.fragment_sync_status) {

    private val viewModel: ReportViewModel by viewModels {
        ReportViewModelFactory((requireActivity().application as NodoCivicoApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val tvConnectionStatus = view.findViewById<TextView>(R.id.tvConnectionStatus)
        val tvSyncQueue = view.findViewById<TextView>(R.id.tvSyncQueue)
        val tvPendingSync = view.findViewById<TextView>(R.id.tvPendingSync)
        val tvUploadedReports = view.findViewById<TextView>(R.id.tvUploadedReports)
        val btnSyncNow = view.findViewById<Button>(R.id.btnSyncNow)

        viewModel.allReports.observe(viewLifecycleOwner) { reports ->
            val pending = reports.count { !it.isSynced }
            val synced = reports.count { it.isSynced }

            tvPendingSync.text = pending.toString()
            tvUploadedReports.text = synced.toString()
            
            tvSyncQueue.text = "$pending reportes pendientes por sincronizar, ¿desea sincronizar ahora?"
        }

        btnSyncNow.setOnClickListener {
            // Verificación manual de conexión antes de iniciar (opcional pero ayuda al feedback)
            viewModel.syncReports()
        }

        viewModel.syncStatus.observe(viewLifecycleOwner) { isSyncing ->
            if (isSyncing) {
                btnSyncNow.isEnabled = false
                btnSyncNow.text = "Sincronizando..."
            } else {
                btnSyncNow.isEnabled = true
                btnSyncNow.text = "Sincronizar ahora"
                
                val pending = viewModel.allReports.value?.count { !it.isSynced } ?: 0
                if (pending == 0) {
                    Toast.makeText(requireContext(), "Sincronización exitosa", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error: No se pudo conectar con el servidor", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
