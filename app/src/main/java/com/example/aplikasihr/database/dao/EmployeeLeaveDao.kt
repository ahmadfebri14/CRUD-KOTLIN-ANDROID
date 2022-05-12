package com.example.aplikasihr.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplikasihr.database.model.EmployeeLeave

@Dao
interface EmployeeLeaveDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employeeLeave: EmployeeLeave)

    @Update
    fun update(employeeLeave: EmployeeLeave)

    @Delete
    fun delete(employeeLeave: EmployeeLeave)

    @Query("SELECT * FROM EmployeeLeave ORDER BY id ASC")
    fun getAllEmployeeLeave(): LiveData<List<EmployeeLeave>>
}