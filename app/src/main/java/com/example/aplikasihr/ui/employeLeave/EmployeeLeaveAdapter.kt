package com.example.aplikasihr.ui.employeLeave

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasihr.database.model.EmployeeLeave
import com.example.aplikasihr.databinding.ItemEmployeeListBinding
import com.example.aplikasihr.helper.EmployeeLeaveDiffCallback
import com.example.aplikasihr.ui.employe.EmployeeAddUpdateActivity

class EmployeeLeaveAdapter : RecyclerView.Adapter<EmployeeLeaveAdapter.EmployeeLeaveViewHolder>() {
    private val listEmployeeLeave = ArrayList<EmployeeLeave>()

    inner class EmployeeLeaveViewHolder(private val binding: ItemEmployeeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employeeLeave: EmployeeLeave) {
            with(binding) {
                tvItemDate.text = employeeLeave.leaveDate
                tvItemTitle.text = employeeLeave.nik
                tvItemDescription.text = employeeLeave.note

                cvItemEmployee.setOnClickListener {
                    val intent = Intent(it.context, EmployeeLeaveAddUpdateActivity::class.java)
                    intent.putExtra(EmployeeLeaveAddUpdateActivity.EXTRA_LEAVE, employeeLeave)
                    it.context.startActivity(intent)
                }
            }
        }
    }


    fun setListEmployeeLeave(pListEmployeeLeave: List<EmployeeLeave>) {
        val diffCallback = EmployeeLeaveDiffCallback(this.listEmployeeLeave, pListEmployeeLeave)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listEmployeeLeave.clear()
        this.listEmployeeLeave.addAll(pListEmployeeLeave)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeLeaveViewHolder {
        val binding =
            ItemEmployeeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeLeaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeLeaveViewHolder, position: Int) {
        holder.bind(listEmployeeLeave[position])
    }

    override fun getItemCount(): Int {
        return listEmployeeLeave.size
    }
}