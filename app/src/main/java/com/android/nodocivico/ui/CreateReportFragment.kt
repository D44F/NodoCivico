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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateReportFragment : Fragment(R.layout.fragment_create_report) {

    private var _binding: FragmentCreateReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels {
        ReportViewModelFactory((requireActivity().application as NodoCivicoApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        _binding = FragmentCreateReportBinding.bind(view)

        setupSpinners()

        binding.btnSaveReport.setOnClickListener {
            validateAndSave()
        }

        binding.btnSaveOffline.setOnClickListener {
            validateAndSaveOffline()
        }
    }

    private fun setupSpinners() {
        val categories = listOf("Alumbrado", "Aseo", "Servicios", "Seguridad", "Zonas verdes")
        val priorities = listOf("Baja", "Media", "Alta")

        binding.spinnerCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        binding.spinnerPriority.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            priorities
        )
    }

    private fun validateAndSave() {
        val title = binding.etReportTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val location = binding.etLocation.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem.toString()
        val priority = binding.spinnerPriority.selectedItem.toString()
        
        val currentTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

        if (title.isEmpty()) {
            binding.etReportTitle.error = "Ingrese un título"
            return
        }

        if (description.isEmpty()) {
            binding.etDescription.error = "Ingrese una descripción"
            return
        }

        if (location.isEmpty()) {
            binding.etLocation.error = "Ingrese una ubicación"
            return
        }

        val report = Report(
            title = title,
            description = description,
            category = category,
            priority = priority,
            location = location,
            time = currentTime,
            status = "Pendiente",
            isSynced = false
        )

        viewModel.insert(report)
        
        Toast.makeText(requireContext(), "Reporte guardado correctamente", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_createReportFragment_to_reportListFragment)
    }

    private fun validateAndSaveOffline() {
        validateAndSave() // En nuestro caso, siempre guarda local primero (Offline-first)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}