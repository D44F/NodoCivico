package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.NodoCivicoApplication
import com.android.nodocivico.R
import com.android.nodocivico.databinding.FragmentCreateReportBinding
import com.android.nodocivico.model.Report
import com.android.nodocivico.viewmodel.ReportViewModel
import com.android.nodocivico.viewmodel.ReportViewModelFactory

class EditReportFragment : Fragment(R.layout.fragment_create_report) {

    private var _binding: FragmentCreateReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels {
        ReportViewModelFactory((requireActivity().application as NodoCivicoApplication).repository)
    }

    private var reportId: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCreateReportBinding.bind(view)
        
        // Cambiamos el texto del botón y título para reusar el diseño
        binding.tvCreateReportTitle.text = "Editar Reporte"
        binding.btnSaveReport.text = "Actualizar"

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        setupSpinners()
        loadReportData()

        binding.btnSaveReport.setOnClickListener {
            updateReport()
        }
        
        binding.btnSaveOffline.visibility = View.GONE
    }

    private fun setupSpinners() {
        val categories = listOf("Alumbrado", "Aseo", "Servicios", "Seguridad", "Zonas verdes")
        val priorities = listOf("Baja", "Media", "Alta")

        binding.spinnerCategory.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        binding.spinnerPriority.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, priorities)
    }

    private fun loadReportData() {
        reportId = arguments?.getInt("reportId") ?: -1
        val title = arguments?.getString("reportTitle")
        val description = arguments?.getString("reportDescription")
        val location = arguments?.getString("reportLocation")

        binding.etReportTitle.setText(title)
        binding.etDescription.setText(description)
        binding.etLocation.setText(location)
    }

    private fun updateReport() {
        val title = binding.etReportTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val location = binding.etLocation.text.toString().trim()

        if (title.isEmpty() || description.isEmpty() || location.isEmpty()) {
            Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Buscamos el reporte original para no perder la fecha y el estado
        // En una app más compleja usaríamos LiveData, aquí lo haremos simple para el MVP
        val updatedReport = Report(
            id = reportId,
            title = title,
            description = description,
            category = binding.spinnerCategory.selectedItem.toString(),
            priority = binding.spinnerPriority.selectedItem.toString(),
            location = location,
            time = arguments?.getString("reportTime") ?: "",
            status = arguments?.getString("reportStatus") ?: "Pendiente",
            isSynced = false
        )

        viewModel.update(updatedReport)
        Toast.makeText(requireContext(), "Reporte actualizado", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack(R.id.reportListFragment, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}