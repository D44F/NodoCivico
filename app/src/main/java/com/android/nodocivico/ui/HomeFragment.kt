package com.android.nodocivico.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.NodoCivicoApplication
import com.android.nodocivico.R
import com.android.nodocivico.databinding.FragmentHomeBinding
import com.android.nodocivico.viewmodel.ReportViewModel
import com.android.nodocivico.viewmodel.ReportViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels {
        ReportViewModelFactory((requireActivity().application as NodoCivicoApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        setupObservers()
        updateConnectionStatus()

        val userName = arguments?.getString("userName") ?: "Usuario"
        binding.tvWelcomeName.text = userName

        binding.btnNewReport.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createReportFragment)
        }

        binding.btnViewReports.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_reportListFragment)
        }

        binding.btnSync.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_syncStatusFragment)
        }
    }

    private fun setupObservers() {
        viewModel.allReports.observe(viewLifecycleOwner) { reports ->
            val total = reports.size
            val pendingSync = reports.count { !it.isSynced }
            val synced = reports.count { it.isSynced }

            binding.tvTotalReports.text = total.toString()
            binding.tvPendingReports.text = pendingSync.toString()
            binding.tvSyncedReports.text = synced.toString()

            if (pendingSync > 0) {
                binding.tvSyncStatusDescription.text = "Tienes $pendingSync reportes pendientes de subir al servidor."
                binding.tvSyncStatusDescription.setTextColor(android.graphics.Color.parseColor("#DC2626"))
            } else {
                binding.tvSyncStatusDescription.text = "Todos los reportes están sincronizados correctamente."
                binding.tvSyncStatusDescription.setTextColor(android.graphics.Color.parseColor("#16A34A"))
            }
        }
    }

    private fun updateConnectionStatus() {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(network)
        
        // Soporte tanto para WiFi como para Datos Móviles
        val isOnline = capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || 
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false

        if (isOnline) {
            binding.tvConnectionStatus.text = "● Conectado (Online)"
            binding.tvConnectionStatus.setTextColor(android.graphics.Color.parseColor("#16A34A"))
        } else {
            binding.tvConnectionStatus.text = "○ Sin conexión (Offline)"
            binding.tvConnectionStatus.setTextColor(android.graphics.Color.parseColor("#DC2626"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
