package com.example.aplikasihr.ui.employeLeave.reportLeave

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasihr.database.model.ReportLeaveQuota
import com.example.aplikasihr.databinding.ItemReportLeaveListBinding
import com.example.aplikasihr.helper.ReportLeaveDiffCallback
import com.example.aplikasihr.ui.employeLeave.EmployeeLeaveAddUpdateActivity

class ReportLeaveAdapter : RecyclerView.Adapter<ReportLeaveAdapter.ReportLeaveViewHolder>() {
    private val listReportLeave = ArrayList<ReportLeaveQuota>()

    inner class ReportLeaveViewHolder(private val binding: ItemReportLeaveListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employeeLeave: ReportLeaveQuota) {
            with(binding) {
                tvItemLeaveNik.text = employeeLeave.nik
                tvItemLeaveName.text = employeeLeave.name
                tvItemLeaveDeduction.text = employeeLeave.quotaDeduction.toString()
                tvItemLeaveQuota.text = employeeLeave.quotaLeave.toString()
                tvItemRemainQuota.text = employeeLeave.quotaRemain.toString()

                cvItemEmployee.setOnClickListener {
                    val intent = Intent(it.context, EmployeeLeaveAddUpdateActivity::class.java)
                    intent.putExtra(EmployeeLeaveAddUpdateActivity.EXTRA_LEAVE, employeeLeave)
                    it.context.startActivity(intent)
                }
            }
        }
    }


    fun setListReportLeave(pListReportLeave: List<ReportLeaveQuota>) {
        val diffCallback = ReportLeaveDiffCallback(this.listReportLeave, pListReportLeave)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listReportLeave.clear()
        this.listReportLeave.addAll(pListReportLeave)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportLeaveViewHolder {
        val binding =
            ItemReportLeaveListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportLeaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportLeaveViewHolder, position: Int) {
        holder.bind(listReportLeave[position])
    }

    override fun getItemCount(): Int {
        return listReportLeave.size
    }
}