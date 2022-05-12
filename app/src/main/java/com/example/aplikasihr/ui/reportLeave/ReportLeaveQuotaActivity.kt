package com.example.aplikasihr.ui.employeLeave.reportLeave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasihr.R
import com.example.aplikasihr.databinding.ActivityReportLeaveQuotaBinding
import com.example.aplikasihr.helper.ViewModelFactory
import com.example.aplikasihr.ui.employeLeave.EmployeeLeaveViewModel

class ReportLeaveQuotaActivity : AppCompatActivity() {
    private var activityReportLeaveQuotaBinding: ActivityReportLeaveQuotaBinding? = null
    private val binding get() = activityReportLeaveQuotaBinding
    private lateinit var adapter: ReportLeaveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityReportLeaveQuotaBinding = ActivityReportLeaveQuotaBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewModel = obtainViewModel(this)
        viewModel.getReportLeaveQuota().observe(this, { report ->
            if (report != null) {
                adapter.setListReportLeave(report)
            }
        })

        adapter = ReportLeaveAdapter()

        binding?.rvReportLeaveList?.layoutManager = LinearLayoutManager(this)
        binding?.rvReportLeaveList?.setHasFixedSize(true)
        binding?.rvReportLeaveList?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        activityReportLeaveQuotaBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity) : ReportLeaveViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ReportLeaveViewModel::class.java)
    }
}