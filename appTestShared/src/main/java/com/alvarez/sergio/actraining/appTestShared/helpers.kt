package com.alvarez.sergio.actraining.appTestShared

import com.alvarez.sergio.actraining.data.Links
import com.alvarez.sergio.actraining.data.NasaResponse
import com.alvarez.sergio.actraining.data.NearEarthObjects
import com.alvarez.sergio.actraining.data.database.NeoEntity
import com.alvarez.sergio.actraining.data.database.NeoRoomDataSource
import com.alvarez.sergio.actraining.data.repositories.NeoRepository
import com.alvarez.sergio.actraining.data.repositories.RegionRepository
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.alvarez.sergio.actraining.ui.FakeLocationDataSource
import com.alvarez.sergio.actraining.ui.FakeNeoDao
import com.alvarez.sergio.actraining.ui.FakePermissionChecker
import com.alvarez.sergio.actraining.ui.FakeRemoteDataSource

fun buildRepositoryWith(
    localData: List<NeoEntity>,
    remoteData: List<NeoEntityDomain>
): NeoRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = NeoRoomDataSource(FakeNeoDao(localData))
    val remoteDataSource = FakeRemoteDataSource().apply { neos = remoteData }
    // return NeoRepository(regionRepository, localDataSource, remoteDataSource)
    return NeoRepository(localDataSource, remoteDataSource)
}

fun buildDatabaseNeos(vararg id: Int) = id.map {
    NeoEntity(
        id = it,
        name = "Name $it",
        absolute_magnitude_h = 22.5,
        imageAssigned = 4,
        is_potentially_hazardous_asteroid = false,
        nasa_jpl_url = "http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=54088823",
        is_sentry_object = false,
        neo_reference_id = 54088823
    )
}

fun buildRemoteNeos(vararg id: Int) = id.map {
    NasaResponse(
        nearEarthObjects = NearEarthObjects(
            dateOne = ArrayList<NeoEntityDomain>(),
            dateTwo = ArrayList<NeoEntityDomain>()
        ),
        links = Links(
            next = "next",
            previous = "prev",
            self = "self"
        )
    )
    Links(
        next = "next",
        previous = "prev",
        self = "self"
    )

    NearEarthObjects(
        dateOne = ArrayList<NeoEntityDomain>(),
        dateTwo = ArrayList<NeoEntityDomain>()
    )
}
