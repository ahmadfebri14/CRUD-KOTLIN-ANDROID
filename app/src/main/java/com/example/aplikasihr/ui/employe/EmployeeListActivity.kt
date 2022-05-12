package com.example.aplikasihr.ui.employe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasihr.R
import com.example.aplikasihr.databinding.ActivityEmployeeListBinding
import com.example.aplikasihr.helper.ViewModelFactory

class EmployeeListActivity : AppCompatActivity() {
    private var activityEmployeeListBinding: ActivityEmployeeListBinding? = null
    private val binding get() = activityEmployeeListBinding
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEmployeeListBinding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewModel = obtainViewModel(this)
        viewModel.getAllEmployee().observe(this, { employeeList ->
            if (employeeList != null) {
                adapter.setListEmployee(employeeList)
            }
        })

        adapter = EmployeeAdapter()

        binding?.rvEmployeeList?.layoutManager = LinearLayoutManager(this)
        binding?.rvEmployeeList?.setHasFixedSize(true)
        binding?.rvEmployeeList?.adapter = adapter

        binding?.fabAddEmployee?.setOnClickListener { view ->
            if (view.id == R.id.fab_add_employee) {
                val intent = Intent(this, EmployeeAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityEmployeeListBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity) : EmployeeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(EmployeeViewModel::class.java)
    }
}