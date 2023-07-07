package com.alvarez.sergio.actraining.data.server

import android.util.Log
import com.alvarez.sergio.actraining.data.datasource.NeoRemoteDataSource
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.alvarez.sergio.actraining.okhttp.getRequest
import com.alvarez.sergio.actraining.ui.datamanagement.composeNeosListFromNasaResponse
import extensions.NASA_OPEN_API_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NeoServerDataSource @Inject constructor() : NeoRemoteDataSource {

    val TAG: String = NeoServerDataSource::class.java.simpleName

    override suspend fun fetchNeos(): ArrayList<NeoEntityDomain> {
        var neosListComposed: ArrayList<NeoEntityDomain> = ArrayList()

        val nasaResponse = withContext(Dispatchers.IO) {
            getRequest(NASA_OPEN_API_URL)
        }

        if (nasaResponse != null) {
            neosListComposed = composeNeosListFromNasaResponse(nasaResponse)
        } else {
            // TODO Warning to user
            Log.e(TAG, "Error: Get request returned no response - Neos response is empty")
        }

        return neosListComposed
    }
}
