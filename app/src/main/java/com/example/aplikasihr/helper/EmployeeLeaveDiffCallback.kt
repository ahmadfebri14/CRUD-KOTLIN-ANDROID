package com.example.aplikasihr.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.aplikasihr.database.model.Employee
import com.example.aplikasihr.database.model.EmployeeLeave

class EmployeeLeaveDiffCallback(private val mOldEmployeeLeaveList: List<EmployeeLeave>, private val mNewEmployeeLeaveList: List<EmployeeLeave>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldEmployeeLeaveList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeLeaveList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeLeaveList[oldItemPosition].id == mNewEmployeeLeaveList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldEmployeeLeaveList[oldItemPosition]
        val newEmployee = mNewEmployeeLeaveList[newItemPosition]
        return oldEmployee.nik == newEmployee.nik
                && oldEmployee.leaveDate == newEmployee.leaveDate
                && oldEmployee.leaveDuration == newEmployee.leaveDuration
                && oldEmployee.note == newEmployee.note
    }
}