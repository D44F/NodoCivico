package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.nodocivico.NodoCivicoApplication
import com.android.nodocivico.R
import com.android.nodocivico.adapter.ReportAdapter
import com.android.nodocivico.databinding.FragmentReportListBinding
import com.android.nodocivico.viewmodel.ReportViewModel
import com.android.nodocivico.viewmodel.ReportViewModelFactory

class ReportListFragment : Fragment(R.layout.fragment_report_list) {

    private var _binding: FragmentReportListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels {
        ReportViewModelFactory((requireActivity().application as NodoCivicoApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReportListBinding.bind(view)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvReports.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ReportAdapter(emptyList()) { report ->
            val bundle = Bundle().apply {
                putInt("reportId", report.id)
                putString("reportTitle", report.title)
                putString("reportDescription", report.description)
                putString("reportCategory", report.category)
                putString("reportPriority", report.priority)
                putString("reportLocation", report.location)
                putString("reportTime", report.time)
                putString("reportStatus", report.status)
            }

            findNavController().navigate(
                R.id.action_reportListFragment_to_reportDetailFragment,
                bundle,
            )
        }

        binding.rvReports.adapter = adapter

        // Aquí es donde sucede la "magia" de observar la base de datos
        viewModel.allReports.observe(viewLifecycleOwner) { reports ->
            reports?.let {
                adapter.setReports(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
