package com.android.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.nodocivico.R
import com.android.nodocivico.adapter.ReportAdapter
import com.android.nodocivico.model.Report

class ReportListFragment : Fragment(R.layout.fragment_report_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val rvReports = view.findViewById<RecyclerView>(R.id.rvReports)

        val reports = listOf(
            Report(
                id = 1,
                title = "Luminaria dañada en la calle 12",
                category = "Alumbrado",
                priority = "Alta",
                location = "Calle 12",
                time = "Hace 2 h",
                status = "Abierto"
            ),
            Report(
                id = 2,
                title = "Basura acumulada en el parque central",
                category = "Aseo",
                priority = "Media",
                location = "Parque central",
                time = "Hace 6 h",
                status = "En proceso"
            ),
            Report(
                id = 3,
                title = "Fuga de agua frente al bloque B",
                category = "Servicios",
                priority = "Alta",
                location = "Bloque B",
                time = "Hoy",
                status = "Cerrado"
            )
        )

        val adapter = ReportAdapter(reports) { report ->
            val bundle = Bundle().apply {
                putInt("reportId", report.id)
                putString("reportTitle", report.title)
                putString("reportCategory", report.category)
                putString("reportPriority", report.priority)
                putString("reportLocation", report.location)
                putString("reportTime", report.time)
                putString("reportStatus", report.status)
            }

            findNavController().navigate(
                R.id.action_reportListFragment_to_reportDetailFragment,
                bundle
            )
        }

        rvReports.layoutManager = LinearLayoutManager(requireContext())
        rvReports.adapter = adapter
    }
}