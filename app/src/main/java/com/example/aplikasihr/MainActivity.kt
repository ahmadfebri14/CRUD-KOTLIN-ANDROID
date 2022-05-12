package com.example.aplikasihr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplikasihr.databinding.ActivityMainBinding
import com.example.aplikasihr.ui.employe.EmployeeListActivity
import com.example.aplikasihr.ui.employeLeave.EmployeeLeaveListActivity

class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
    private val binding get() = activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.cardEmployee?.setOnClickListener {
            val intent = Intent(this, EmployeeListActivity::class.java)
            startActivity(intent)
        }

        binding?.cardEmployeeLeave?.setOnClickListener {
            val intent = Intent(this, EmployeeLeaveListActivity::class.java)
            startActivity(intent)
        }
    }
}