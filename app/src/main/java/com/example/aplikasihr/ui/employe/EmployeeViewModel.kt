package com.example.aplikasihr.ui.employe

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasihr.database.model.Employee
import com.example.aplikasihr.repository.EmployeeRepository

class EmployeeViewModel(application: Application) : ViewModel() {
    private val mEmployeeRepository: EmployeeRepository = EmployeeRepository(application)
    private val listRole = MutableLiveData<List<String>>()

    fun fetchSpinnerItems(): LiveData<List<String>> {
        //fetch data
        var a: ArrayList<String> = ArrayList()
        a.add("Mobile Developer")
        a.add("Front End")
        a.add("Back End")
        a.add("Project Manager")
        a.add("SAP Consultant")
        a.add("System Analyst")
        listRole.postValue(a)
        return listRole
    }

    fun getAllEmployee() : LiveData<List<Employee>> = mEmployeeRepository.getAllEmployee()

    fun insertEmployee(employee: Employee) {
        mEmployeeRepository.insert(employee)
    }

    fun updateEmployee(employee: Employee) {
        mEmployeeRepository.update(employee)
    }

    fun deleteEmployee(employee: Employee) {
        mEmployeeRepository.delete(employee)
    }
}