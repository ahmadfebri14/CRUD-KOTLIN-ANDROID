package com.example.aplikasihr.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.aplikasihr.database.HrDatabase
import com.example.aplikasihr.database.dao.EmployeeDao
import com.example.aplikasihr.database.model.Employee
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EmployeeRepository(application: Application) {
    private val mEmployeeDao: EmployeeDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HrDatabase.getDatabase(application)
        mEmployeeDao = db.employeeDao()
    }

    fun getAllEmployee(): LiveData<List<Employee>> = mEmployeeDao.getAllEmployee()
    fun getAllEmployeeName(): LiveData<List<String>> = mEmployeeDao.getAllEmployeeName()

    fun insert(employee: Employee) {
        executorService.execute {
            mEmployeeDao.insert(employee)
        }
    }

    fun update(employee: Employee) {
        executorService.execute {
            mEmployeeDao.update(employee)
        }
    }

    fun delete(employee: Employee) {
        executorService.execute {
            mEmployeeDao.delete(employee)
        }
    }
}