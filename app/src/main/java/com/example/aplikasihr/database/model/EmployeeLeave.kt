package com.example.aplikasihr.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class EmployeeLeave(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nik: String? = null,
    var nama: String? = null,
    var leaveDuration: Int? = null,
    var leaveDate: String? = null,
    var note: String? = null
) : Parcelable {
}