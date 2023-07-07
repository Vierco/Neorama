package com.alvarez.sergio.actraining.data.repositories

import com.alvarez.sergio.actraining.data.datasource.LocationDataSource
import javax.inject.Inject

class RegionRepository @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        const val DEFAULT_REGION = "ES"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}
