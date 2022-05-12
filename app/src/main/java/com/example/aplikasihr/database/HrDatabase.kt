package com.example.aplikasihr.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplikasihr.database.dao.EmployeeDao
import com.example.aplikasihr.database.dao.EmployeeLeaveDao
import com.example.aplikasihr.database.model.Employee
import com.example.aplikasihr.database.model.EmployeeLeave

@Database(entities = [Employee::class, EmployeeLeave::class], version = 1)
abstract class HrDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: HrDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): HrDatabase {
            if (INSTANCE == null) {
                synchronized(HrDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HrDatabase::class.java, "hr_database"
                    ).build()
                }
            }
            return INSTANCE as HrDatabase
        }
    }

    abstract fun employeeDao(): EmployeeDao
    abstract fun employeeLeaveDao(): EmployeeLeaveDao
}