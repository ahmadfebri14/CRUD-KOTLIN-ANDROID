package com.example.aplikasihr.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplikasihr.database.model.Employee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)

    @Query("SELECT * FROM Employee ORDER BY id ASC")
    fun getAllEmployee(): LiveData<List<Employee>>

    @Query("SELECT name FROM Employee ORDER BY id ASC")
    fun getAllEmployeeName(): LiveData<List<String>>
}