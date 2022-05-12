package com.example.aplikasihr.ui.employeLeave

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasihr.database.model.Employee
import com.example.aplikasihr.database.model.EmployeeLeave
import com.example.aplikasihr.repository.EmployeeLeaveRepository
import com.example.aplikasihr.repository.EmployeeRepository

class EmployeeLeaveViewModel(application: Application) : ViewModel() {
    private val mEmployeeLeaveRepository: EmployeeLeaveRepository = EmployeeLeaveRepository(application)
    private val mEmployeeRepository: EmployeeRepository = EmployeeRepository(application)

    fun getAllEmployeeLeave() : LiveData<List<EmployeeLeave>> = mEmployeeLeaveRepository.getAllEmployeeLeave()
    fun getAllEmployee() : LiveData<List<Employee>> = mEmployeeRepository.getAllEmployee()

    fun insert(employeeLeave: EmployeeLeave) {
        mEmployeeLeaveRepository.insert(employeeLeave)
    }

    fun update(employeeLeave: EmployeeLeave) {
        mEmployeeLeaveRepository.update(employeeLeave)
    }

    fun delete(employeeLeave: EmployeeLeave) {
        mEmployeeLeaveRepository.delete(employeeLeave)
    }
}