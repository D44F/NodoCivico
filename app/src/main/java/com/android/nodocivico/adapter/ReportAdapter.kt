package com.android.nodocivico.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.nodocivico.R
import com.android.nodocivico.model.Report

class ReportAdapter(
    private var reports: List<Report>,
    private val listener: (Report) -> Unit
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    fun setReports(newReports: List<Report>) {
        this.reports = newReports
        notifyDataSetChanged()
    }

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvReportInitial: TextView = itemView.findViewById(R.id.tvReportInitial)
        val tvReportTitle: TextView = itemView.findViewById(R.id.tvReportTitle)
        val tvReportInfo: TextView = itemView.findViewById(R.id.tvReportInfo)
        val tvReportTime: TextView = itemView.findViewById(R.id.tvReportTime)
        val tvReportStatus: TextView = itemView.findViewById(R.id.tvReportStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = reports[position]

        holder.tvReportInitial.text = report.title.firstOrNull()?.uppercase() ?: "R"
        holder.tvReportTitle.text = report.title
        holder.tvReportInfo.text = "Categoría: ${report.category} · Prioridad ${report.priority}"
        holder.tvReportTime.text = report.time
        holder.tvReportStatus.text = report.status

        when (report.status) {
            "Abierto", "Pendiente" -> {
                holder.tvReportStatus.setBackgroundResource(R.drawable.bg_status_open)
                holder.tvReportStatus.setTextColor(Color.parseColor("#2563EB"))
            }
            "En proceso" -> {
                holder.tvReportStatus.setBackgroundResource(R.drawable.bg_status_progress)
                holder.tvReportStatus.setTextColor(Color.parseColor("#A16207"))
            }
            "Cerrado", "Completado" -> {
                holder.tvReportStatus.setBackgroundResource(R.drawable.bg_status_closed)
                holder.tvReportStatus.setTextColor(Color.parseColor("#16A34A"))
            }
        }

        holder.itemView.setOnClickListener { listener(report) }
    }

    override fun getItemCount(): Int = reports.size
}