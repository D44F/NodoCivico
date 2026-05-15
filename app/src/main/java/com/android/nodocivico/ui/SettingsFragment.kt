package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.R
import com.android.nodocivico.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        _binding = FragmentSettingsBinding.bind(view)

        setupSpinners()

        binding.btnSaveSettings.setOnClickListener {
            Toast.makeText(requireContext(), "Preferencias guardadas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinners() {
        binding.spinnerTheme.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Sistema", "Claro", "Oscuro")
        )

        binding.spinnerNotifications.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Activas", "Desactivadas")
        )

        binding.spinnerStatusFilter.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Todos", "Abierto", "En proceso", "Cerrado")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}