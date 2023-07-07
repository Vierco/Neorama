package com.alvarez.sergio.actraining.data

import com.alvarez.sergio.actraining.data.datasource.LocationDataSource
import com.alvarez.sergio.actraining.data.repositories.PermissionChecker
import com.alvarez.sergio.actraining.data.repositories.PermissionChecker.Permission.COARSE_LOCATION
import com.alvarez.sergio.actraining.data.repositories.RegionRepository
import com.alvarez.sergio.actraining.data.repositories.RegionRepository.Companion.DEFAULT_REGION
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RegionRepositoryTest {

    // Using mocks
    @Test
    fun `Permission COARSE LOCATION disbaled, check the default findLastLocation() response is "ES" whit mocks`(): Unit =
        runBlocking {
            val sut = RegionRepository(
                mock(), mock()
            )

            val responseFindLastRegion = sut.findLastRegion()

            assertEquals(DEFAULT_REGION, responseFindLastRegion)
        }

    // Managing mocks
    @Test
    fun `Permission COARSE LOCATION disbaled, check the default findLastLocation() response is "ES" managing mocks`(): Unit =
        runBlocking {
            val sut = RegionRepository(
                locationDataSource = mock(),
                permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn false }
            )

            val responseFindLastRegion = sut.findLastRegion()

            assertEquals(DEFAULT_REGION, responseFindLastRegion)
        }

    @Test
    fun `Check region prefix when permission is granted`(): Unit =
        runBlocking {
            val sut = RegionRepository(
                locationDataSource = mock { onBlocking { findLastRegion() } doReturn "ES" },
                permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn true }
            )

            val responseFindLastRegion = sut.findLastRegion()

            assertEquals("ES", responseFindLastRegion)
        }

    // Overwrite the building of RegionRepository
    @Test
    fun `Permission COARSE LOCATION disbaled, check the default findLastLocation() response is "ES" adding config fun`(): Unit =
        runBlocking {
            val sut = configRegionRepository(
                permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn false }
            )

            val responseFindLastRegion = sut.findLastRegion()

            assertEquals(DEFAULT_REGION, responseFindLastRegion)
        }
}

private fun configRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
) =
    RegionRepository(locationDataSource, permissionChecker)
