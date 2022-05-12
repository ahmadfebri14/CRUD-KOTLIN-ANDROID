package com.example.aplikasihr.ui.employeLeave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasihr.R
import com.example.aplikasihr.databinding.ActivityEmployeeLeaveListBinding
import com.example.aplikasihr.helper.ViewModelFactory
import com.example.aplikasihr.ui.employe.EmployeeAddUpdateActivity
import com.example.aplikasihr.ui.employe.EmployeeViewModel

class EmployeeLeaveListActivity : AppCompatActivity() {
    private var activityEmployeeLeaveListBinding: ActivityEmployeeLeaveListBinding? = null
    private val binding get() = activityEmployeeLeaveListBinding
    private lateinit var adapter: EmployeeLeaveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEmployeeLeaveListBinding = ActivityEmployeeLeaveListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewModel = obtainViewModel(this)
        viewModel.getAllEmployeeLeave().observe(this, { employeeLeaveList ->
            if (employeeLeaveList != null) {
                adapter.setListEmployeeLeave(employeeLeaveList)
            }
        })

        adapter = EmployeeLeaveAdapter()

        binding?.rvEmployeeLeaveList?.layoutManager = LinearLayoutManager(this)
        binding?.rvEmployeeLeaveList?.setHasFixedSize(true)
        binding?.rvEmployeeLeaveList?.adapter = adapter

        binding?.fabAddEmployeeLeave?.setOnClickListener { view ->
            if (view.id == R.id.fab_add_employee_leave) {
                val intent = Intent(this, EmployeeLeaveAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityEmployeeLeaveListBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity) : EmployeeLeaveViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(EmployeeLeaveViewModel::class.java)
    }
}