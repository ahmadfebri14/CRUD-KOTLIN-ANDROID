package com.example.aplikasihr.ui.employe

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.DateKeyListener
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasihr.R
import com.example.aplikasihr.database.model.Employee
import com.example.aplikasihr.databinding.ActivityEmployeeAddUpdateBinding
import com.example.aplikasihr.helper.DateHelper
import com.example.aplikasihr.helper.ViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import android.R.string.no
import kotlin.collections.ArrayList


class EmployeeAddUpdateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    companion object {
        const val EXTRA_EMPLOYEE = "extra_employee"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var cal = Calendar.getInstance()
    private var selectedDate: String = ""
    private var selectedRole: String = ""
    private lateinit var listOfItem: ArrayList<String>
    private var isEdit = false
    private var employee: Employee? = null
    private lateinit var employeeViewModel: EmployeeViewModel
    private var activityEmployeeAddUpdateBinding: ActivityEmployeeAddUpdateBinding? = null
    private val binding get() = activityEmployeeAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBarTitle: String
        val btnTitle: String

        activityEmployeeAddUpdateBinding = ActivityEmployeeAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        employeeViewModel = obtainViewModel(this)

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        employee = intent.getParcelableExtra(EXTRA_EMPLOYEE)
        if (employee != null) {
            isEdit = true
        } else {
            employee = Employee()
        }

        binding?.edtBirthDate?.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (employee != null) {
                employee?.let { employee ->
                    binding?.edtNik?.setText(employee.nik)
                    binding?.edtName?.setText(employee.name)
                    binding?.edtAddress?.setText(employee.address)
                    binding?.edtBirthDate?.setText(employee.birthDate)
                    binding?.edtPhone?.setText(employee.phoneNumber)
                    selectedDate = employee.birthDate.toString()
                    selectedRole = employee.role.toString()
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnSubmitEmployee?.text = btnTitle

        binding?.btnSubmitEmployee?.setOnClickListener {
            val nik = binding?.edtNik?.text.toString().trim()
            val name = binding?.edtName?.text.toString().trim()
            val phone = binding?.edtPhone?.text.toString().trim()
            val address = binding?.edtAddress?.text.toString().trim()
            val date = binding?.edtBirthDate?.text.toString().trim()
            when {
                nik.isEmpty() -> {
                    binding?.edtNik?.error = getString(R.string.empty)
                }
                name.isEmpty() -> {
                    binding?.edtName?.error = getString(R.string.empty)
                }
                phone.isEmpty() -> {
                    binding?.edtPhone?.error = getString(R.string.empty)
                }
                address.isEmpty() -> {
                    binding?.edtAddress?.error = getString(R.string.empty)
                }
                date.isEmpty() -> {
                    binding?.edtBirthDate?.error = getString(R.string.empty)
                }
                else -> {
                    employee.let { employee ->
                        employee?.nik = nik
                        employee?.name = name
                        employee?.role = selectedRole
                        employee?.phoneNumber = phone
                        employee?.address = address
                        employee?.birthDate = date
                    }
                    if (isEdit) {
                        employeeViewModel.updateEmployee(employee as Employee)
                        showToast(getString(R.string.changed))
                    } else {
                        employeeViewModel.insertEmployee(employee as Employee)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
        setDateTimePicker()
        binding?.edtRole?.setOnItemSelectedListener(this)
        setSpinnerRole()

    }

    private fun setDateTimePicker() {
        //set date picker
        if (isEdit) {
            val formatter = SimpleDateFormat("yyyy/MM/dd")
            val date = formatter.parse(selectedDate)
            cal.time = date
            binding?.edtBirthDate?.setText(selectedDate)
        } else {
            updateDateInView()
        }
    }

    private fun setSpinnerRole() {
        employeeViewModel.fetchSpinnerItems().observe(this, androidx.lifecycle.Observer {
            val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.edtRole?.adapter = adapterSpinner

            if (isEdit) {
                for (i in 0 until it.size) {
                    if (binding?.edtRole?.getItemAtPosition(i).toString().equals(selectedRole)) {
                        binding?.edtRole?.setSelection(i)
                        break
                    }
                }
            }
            listOfItem = ArrayList()
            listOfItem.addAll(it)
        })
    }

    private fun updateDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding?.edtBirthDate?.text = sdf.format(cal.getTime())
    }

    private fun obtainViewModel(activity: AppCompatActivity): EmployeeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(EmployeeViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        activityEmployeeAddUpdateBinding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    employeeViewModel.deleteEmployee(employee as Employee)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        selectedRole = listOfItem[position]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}