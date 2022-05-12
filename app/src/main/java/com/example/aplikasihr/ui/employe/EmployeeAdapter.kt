package com.example.aplikasihr.ui.employe

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasihr.database.model.Employee
import com.example.aplikasihr.databinding.ItemEmployeeListBinding
import com.example.aplikasihr.helper.EmployeeDiffCallback

class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    private val listEmployee = ArrayList<Employee>()

    inner class EmployeeViewHolder(private val binding: ItemEmployeeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            with(binding) {
                tvItemDate.text = employee.birthDate
                tvItemTitle.text = employee.nik
                tvItemDescription.text = employee.name

                cvItemEmployee.setOnClickListener {
                    val intent = Intent(it.context, EmployeeAddUpdateActivity::class.java)
                    intent.putExtra(EmployeeAddUpdateActivity.EXTRA_EMPLOYEE, employee)
                    it.context.startActivity(intent)
                }
            }
        }
    }


    fun setListEmployee(pListEmployee: List<Employee>) {
        val diffCallback = EmployeeDiffCallback(this.listEmployee, pListEmployee)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listEmployee.clear()
        this.listEmployee.addAll(pListEmployee)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding =
            ItemEmployeeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(listEmployee[position])
    }

    override fun getItemCount(): Int {
        return listEmployee.size
    }
}
