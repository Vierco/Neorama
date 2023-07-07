package com.alvarez.sergio.actraining.ui.datamanagement

import com.alvarez.sergio.actraining.data.NasaResponse
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import extensions.MAX_IMAGES
import extensions.TODAY
import extensions.YESTERDAY
import org.json.JSONObject

fun composeNeosListFromNasaResponse(neoList: String): ArrayList<NeoEntityDomain> {
    var json = JSONObject(neoList).toString()
    json = json.replace(YESTERDAY.toString(), "dateOne")
    json = json.replace(TODAY.toString(), "dateTwo")

    val nasaResponseClass: NasaResponse? = JsonParser.parsing.fromJson(json, NasaResponse::class.java)

    // TODO Improve nullable handling
    val todayArray: ArrayList<NeoEntityDomain> = nasaResponseClass?.nearEarthObjects?.dateOne!!
    todayArray.addAll(nasaResponseClass.nearEarthObjects.dateTwo)

    for (i in 0 until todayArray.size) {
        val randomImage = (0..MAX_IMAGES).shuffled().last()
        todayArray[i].imageAssigned = randomImage
    }

    return todayArray
}
