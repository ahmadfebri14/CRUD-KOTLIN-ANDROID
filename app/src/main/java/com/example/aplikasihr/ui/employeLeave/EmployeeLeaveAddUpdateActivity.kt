package com.example.aplikasihr.ui.employeLeave

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.aplikasihr.database.model.EmployeeLeave
import com.example.aplikasihr.databinding.ActivityEmployeeLeaveAddUpdateBinding
import com.example.aplikasihr.helper.ViewModelFactory
import com.example.aplikasihr.ui.employe.EmployeeAddUpdateActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmployeeLeaveAddUpdateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    companion object {
        const val EXTRA_LEAVE = "extra_leave"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var cal = Calendar.getInstance()
    private var selectedNik: String = ""
    private var selectedName: String = ""
    private var listOfItemNik: ArrayList<String>? = null
    private var listOfItemName: ArrayList<String>? = null
    private var selectedDate: String = ""
    private var isEdit = false
    private var employeeLeave: EmployeeLeave? = null
    private lateinit var employeeLeaveViewModel: EmployeeLeaveViewModel
    private var activityEmployeeLeaveAddUpdateBinding: ActivityEmployeeLeaveAddUpdateBinding? = null
    private val binding get() = activityEmployeeLeaveAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_leave_add_update)

        val actionBarTitle: String
        val btnTitle: String

        activityEmployeeLeaveAddUpdateBinding =
            ActivityEmployeeLeaveAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        employeeLeaveViewModel = obtainViewModel(this)

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

        employeeLeave = intent.getParcelableExtra(EXTRA_LEAVE)
        if (employeeLeave != null) {
            isEdit = true
        } else {
            employeeLeave = EmployeeLeave()
        }

        binding?.edtLeaveDate?.setOnClickListener {
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
            if (employeeLeave != null) {
                employeeLeave?.let { employeeLeave ->
                    binding?.edtNameLeave?.setText(employeeLeave.nama)
                    binding?.edtDurationLeave?.setText(employeeLeave.leaveDuration.toString())
                    binding?.edtNoteLeave?.setText(employeeLeave.note)
                    selectedDate = employeeLeave.leaveDate.toString()
                    selectedNik = employeeLeave.nik.toString().trim()
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnSubmitEmployeeLeave?.text = btnTitle

        binding?.btnSubmitEmployeeLeave?.setOnClickListener {
            val name = binding?.edtNameLeave?.text.toString().trim()
            val leaveDuration = binding?.edtDurationLeave?.text.toString().trim()
            val note = binding?.edtNoteLeave?.text.toString().trim()
            selectedDate = binding?.edtLeaveDate?.text.toString()
            when {
                name.isEmpty() -> {
                    binding?.edtNameLeave?.error = getString(R.string.empty)
                }
                leaveDuration.isEmpty() -> {
                    binding?.edtDurationLeave?.error = getString(R.string.empty)
                }
                note.isEmpty() -> {
                    binding?.edtNoteLeave?.error = getString(R.string.empty)
                }
                else -> {
                    employeeLeave.let { employeeLeave ->
                        employeeLeave?.nik = selectedNik
                        employeeLeave?.nama = name
                        employeeLeave?.leaveDuration = leaveDuration.toInt()
                        employeeLeave?.leaveDate = selectedDate
                        employeeLeave?.note = note
                    }
                    if (isEdit) {
                        employeeLeaveViewModel.update(employeeLeave as EmployeeLeave)
                        showToast(getString(R.string.changed))
                    } else {
                        employeeLeaveViewModel.insert(employeeLeave as EmployeeLeave)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
        setDateTimePicker()
        binding?.edtNikLeave?.setOnItemSelectedListener(this)
        setSpinnerRole()
    }

    private fun setDateTimePicker() {
        //set date picker
        if (isEdit) {
            val formatter = SimpleDateFormat("yyyy/MM/dd")
            val date = formatter.parse(selectedDate)
            cal.time = date
            binding?.edtLeaveDate?.setText(selectedDate)
        } else {
            updateDateInView()
        }
    }

    private fun setSpinnerRole() {
        listOfItemNik = ArrayList()
        listOfItemName = ArrayList()

        val adapterSpinner = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        employeeLeaveViewModel.getAllEmployee()
            .observe(this, { employee ->
                employee.forEach { data ->
                    listOfItemNik?.add(data.nik.toString())
                    listOfItemName?.add(data.name.toString())
                    adapterSpinner.add(data.nik)
                }

                //set adapter
                binding?.edtNikLeave?.adapter = adapterSpinner

                //set current value
                if (isEdit) {
                    val countItem = listOfItemNik?.size ?: 0
                    for (i in 0 until countItem) {
                        if (binding?.edtNikLeave?.getItemAtPosition(i).toString()
                                .equals(selectedNik)
                        ) {
                            binding?.edtNikLeave?.setSelection(i)
                            break
                        }
                    }
                }
            })
    }

    private fun updateDateInView() {
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding?.edtLeaveDate?.text = sdf.format(cal.getTime())
    }

    private fun obtainViewModel(activity: AppCompatActivity): EmployeeLeaveViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(EmployeeLeaveViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        activityEmployeeLeaveAddUpdateBinding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        showAlertDialog(EmployeeAddUpdateActivity.ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == EmployeeAddUpdateActivity.ALERT_DIALOG_CLOSE
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
                    employeeLeaveViewModel.delete(employeeLeave as EmployeeLeave)
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedNik = listOfItemNik?.get(p2).toString()
        selectedName = listOfItemName?.get(p2).toString()
        binding?.edtNameLeave?.setText(selectedName)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}