package com.alvarez.sergio.actraining.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class NeoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val absolute_magnitude_h: Double,
    val imageAssigned: Int,
    val is_potentially_hazardous_asteroid: Boolean,
    val nasa_jpl_url: String,
    val is_sentry_object:Boolean,
    val neo_reference_id: Int
    ) : Parcelable
