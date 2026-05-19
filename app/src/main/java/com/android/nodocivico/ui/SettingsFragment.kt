package com.android.nodocivico.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.R
import com.android.nodocivico.databinding.FragmentSettingsBinding
import com.android.nodocivico.network.RetrofitClient

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { findNavController().navigateUp() }

        _binding = FragmentSettingsBinding.bind(view)

        val prefs = requireContext().getSharedPreferences("nodo_prefs", Context.MODE_PRIVATE)
        binding.etServerIp.setText(prefs.getString("server_ip", "10.0.2.2"))

        setupSpinners()

        binding.btnSaveSettings.setOnClickListener {
            val newIp = binding.etServerIp.text.toString().trim()
            if (newIp.isNotEmpty()) {
                prefs.edit().putString("server_ip", newIp).apply()
                RetrofitClient.reset()
                Toast.makeText(requireContext(), "Configuración actualizada", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
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