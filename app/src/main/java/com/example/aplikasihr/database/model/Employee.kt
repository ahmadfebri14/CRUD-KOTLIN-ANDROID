package com.example.aplikasihr.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Employee(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nik: String? = null,
    var name: String? = null,
    var role: String? = null,
    var address: String? = null,
    var birthDate: String? = null,
    var gender: String? = null,
    var phoneNumber: String? = null) : Parcelable {}