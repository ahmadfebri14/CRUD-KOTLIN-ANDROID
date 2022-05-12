package com.example.aplikasihr.ui.employeLeave.reportLeave

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasihr.database.model.ReportLeaveQuota
import com.example.aplikasihr.repository.EmployeeLeaveRepository

class ReportLeaveViewModel(application: Application) : ViewModel() {
    private val mEmployeeLeaveRepository: EmployeeLeaveRepository = EmployeeLeaveRepository(application)

    fun getReportLeaveQuota() : LiveData<List<ReportLeaveQuota>> = mEmployeeLeaveRepository.getLeaveQuota()
}