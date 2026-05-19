package com.android.nodocivico.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.nodocivico.R;
import com.android.nodocivico.model.Report;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final List<Report> reports;
    private final OnReportClickListener listener;

    public interface OnReportClickListener {
        void onReportClick(Report report);
    }

    public ReportAdapter(List<Report> reports, OnReportClickListener listener) {
        this.reports = reports;
        this.listener = listener;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvReportInitial;
        TextView tvReportTitle;
        TextView tvReportInfo;
        TextView tvReportTime;
        TextView tvReportStatus;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            tvReportInitial = itemView.findViewById(R.id.tvReportInitial);
            tvReportTitle = itemView.findViewById(R.id.tvReportTitle);
            tvReportInfo = itemView.findViewById(R.id.tvReportInfo);
            tvReportTime = itemView.findViewById(R.id.tvReportTime);
            tvReportStatus = itemView.findViewById(R.id.tvReportStatus);
        }
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report, parent, false);

        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report report = reports.get(position);

        String title = report.getTitle();

        if (title != null && !title.isEmpty()) {
            holder.tvReportInitial.setText(title.substring(0, 1).toUpperCase());
        } else {
            holder.tvReportInitial.setText("R");
        }

        holder.tvReportTitle.setText(report.getTitle());
        holder.tvReportInfo.setText(
                "Categoría: " + report.getCategory() + " · Prioridad " + report.getPriority()
        );
        holder.tvReportTime.setText(report.getTime());
        holder.tvReportStatus.setText(report.getStatus());

        if ("Abierto".equals(report.getStatus())) {
            holder.tvReportStatus.setBackgroundResource(R.drawable.bg_status_open);
            holder.tvReportStatus.setTextColor(Color.parseColor("#2563EB"));
        } else if ("En proceso".equals(report.getStatus())) {
            holder.tvReportStatus.setBackgroundResource(R.drawable.bg_status_progress);
            holder.tvReportStatus.setTextColor(Color.parseColor("#A16207"));
        } else if ("Cerrado".equals(report.getStatus())) {
            holder.tvReportStatus.setBackgroundResource(R.drawable.bg_status_closed);
            holder.tvReportStatus.setTextColor(Color.parseColor("#16A34A"));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReportClick(report);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }
}