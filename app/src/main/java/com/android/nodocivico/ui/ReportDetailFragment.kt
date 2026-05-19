package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.NodoCivicoApplication
import com.android.nodocivico.R
import com.android.nodocivico.databinding.FragmentReportDetailBinding
import com.android.nodocivico.model.Report
import com.android.nodocivico.viewmodel.ReportViewModel
import com.android.nodocivico.viewmodel.ReportViewModelFactory

class ReportDetailFragment : Fragment(R.layout.fragment_report_detail) {

    private var _binding: FragmentReportDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels {
        ReportViewModelFactory((requireActivity().application as NodoCivicoApplication).repository)
    }

    private var currentReport: Report? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReportDetailBinding.bind(view)

        val reportId = arguments?.getInt("reportId") ?: -1

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Observamos el reporte para tener los datos actualizados
        viewModel.allReports.observe(viewLifecycleOwner) { reports ->
            currentReport = reports.find { it.id == reportId }
            currentReport?.let { displayReport(it) }
        }

        binding.btnUpdateStatus.setOnClickListener {
            currentReport?.let {
                val nextStatus = when (it.status) {
                    "Pendiente" -> "En proceso"
                    "En proceso" -> "Completado"
                    "Completado" -> "Pendiente"
                    else -> "En proceso"
                }
                val updated = it.copy(status = nextStatus, isSynced = false)
                viewModel.update(updated)
                Toast.makeText(requireContext(), "Estado actualizado a $nextStatus", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnEditReport.setOnClickListener {
            currentReport?.let {
                val bundle = Bundle().apply {
                    putInt("reportId", it.id)
                    putString("reportTitle", it.title)
                    putString("reportDescription", it.description)
                    putString("reportCategory", it.category)
                    putString("reportPriority", it.priority)
                    putString("reportLocation", it.location)
                    putString("reportTime", it.time)
                    putString("reportStatus", it.status)
                }
                findNavController().navigate(R.id.action_reportDetailFragment_to_editReportFragment, bundle)
            }
        }

        binding.btnCreateReminder.setOnClickListener {
            Toast.makeText(requireContext(), "Recordatorio (E3) pendiente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayReport(report: Report) {
        binding.tvReportDetailTitle.text = report.title
        binding.tvReportLocation.text = "Ubicación: ${report.location}"
        binding.tvReportDate.text = "Categoría: ${report.category} · ${report.time}"
        binding.tvReportObservation.text = report.description
        binding.tvReportStatus.text = report.status
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}