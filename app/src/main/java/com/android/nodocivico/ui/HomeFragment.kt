package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.nodocivico.R
import com.android.nodocivico.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}