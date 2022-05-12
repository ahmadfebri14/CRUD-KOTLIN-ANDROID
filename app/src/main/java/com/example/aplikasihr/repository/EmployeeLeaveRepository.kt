package com.example.aplikasihr.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.aplikasihr.database.HrDatabase
import com.example.aplikasihr.database.dao.EmployeeLeaveDao
import com.example.aplikasihr.database.model.EmployeeLeave
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EmployeeLeaveRepository(application: Application) {
    private val mEmployeeLeaveDao: EmployeeLeaveDao
    private val mExecutorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HrDatabase.getDatabase(application)
        mEmployeeLeaveDao = db.employeeLeaveDao()
    }

    fun getAllEmployeeLeave(): LiveData<List<EmployeeLeave>> = mEmployeeLeaveDao.getAllEmployeeLeave()

    fun insert(employeeLeave: EmployeeLeave) {
        mExecutorService.execute {
            mEmployeeLeaveDao.insert(employeeLeave)
        }
    }

    fun update(employeeLeave: EmployeeLeave) {
        mExecutorService.execute {
            mEmployeeLeaveDao.update(employeeLeave)
        }
    }

    fun delete(employeeLeave: EmployeeLeave) {
        mExecutorService.execute {
            mEmployeeLeaveDao.delete(employeeLeave)
        }
    }
}