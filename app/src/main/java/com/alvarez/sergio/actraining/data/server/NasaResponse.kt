package com.alvarez.sergio.actraining.data

import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.google.gson.annotations.SerializedName

data class NasaResponse(
    @SerializedName("near_earth_objects")
    val nearEarthObjects: NearEarthObjects,
    val links: Links
)

class Links(
    var next: String,
    val previous: String,
    val self: String
)

class NearEarthObjects(
    val dateOne: ArrayList<NeoEntityDomain>,
    val dateTwo: ArrayList<NeoEntityDomain>
)
