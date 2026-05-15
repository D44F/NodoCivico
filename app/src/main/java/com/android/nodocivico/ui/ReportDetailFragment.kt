package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.R
import com.android.nodocivico.databinding.FragmentReportDetailBinding

class ReportDetailFragment : Fragment(R.layout.fragment_report_detail) {

    private var _binding: FragmentReportDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        _binding = FragmentReportDetailBinding.bind(view)

        val title = arguments?.getString("reportTitle") ?: "Reporte sin título"
        val category = arguments?.getString("reportCategory") ?: "Sin categoría"
        val priority = arguments?.getString("reportPriority") ?: "Sin prioridad"
        val location = arguments?.getString("reportLocation") ?: "Sin ubicación"
        val status = arguments?.getString("reportStatus") ?: "Sin estado"

        binding.tvReportDetailTitle.text = title
        binding.tvReportLocation.text = "Ubicación: $location"
        binding.tvReportDate.text = "Categoría: $category"
        binding.tvReportObservation.text = "Prioridad: $priority"
        binding.tvReportStatus.text = status

        binding.btnUpdateStatus.setOnClickListener {
            binding.tvReportStatus.text = "En proceso"
            Toast.makeText(requireContext(), "Estado actualizado", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditReport.setOnClickListener {
            Toast.makeText(requireContext(), "Función de edición pendiente", Toast.LENGTH_SHORT).show()
        }

        binding.btnCreateReminder.setOnClickListener {
            Toast.makeText(requireContext(), "Recordatorio simulado creado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}