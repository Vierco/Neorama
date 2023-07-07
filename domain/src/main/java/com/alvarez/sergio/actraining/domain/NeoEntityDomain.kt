package com.alvarez.sergio.actraining.domain

data class NeoEntityDomain(
    val id: Int,
    val name: String,
    val absolute_magnitude_h: Double,
    var imageAssigned: Int,
    val is_potentially_hazardous_asteroid: Boolean,
    val nasa_jpl_url: String,
    val is_sentry_object:Boolean,
    val neo_reference_id: Int
)
