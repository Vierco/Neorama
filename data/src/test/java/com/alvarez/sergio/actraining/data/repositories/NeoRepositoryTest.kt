package com.alvarez.sergio.actraining.data.repositories

import com.alvarez.sergio.actraining.data.datasource.NeoLocalDataSource
import com.alvarez.sergio.actraining.data.datasource.NeoRemoteDataSource
import com.alvarez.sergio.actraining.testcommonsitems.fakeNeo
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class NeoRepositoryTest {

    @Mock
    lateinit var localDataSource: NeoLocalDataSource

    @Mock
    lateinit var remoteDataSource: NeoRemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    private lateinit var neoRepository: NeoRepository

    private val localNeos = flowOf(listOf(fakeNeo.copy(1)))

    @Before
    fun setup() {
        whenever(localDataSource.neos) doReturn localNeos
        neoRepository = NeoRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `Today neos are taken from localDataSource if are availables`(): Unit = runBlocking {
        val result = neoRepository.todayNeos

        assertEquals(localNeos, result)
    }

    @Test
    fun `Switch neoSaved from false to true`(): Unit = runBlocking {
        val neo = fakeNeo.copy(is_potentially_hazardous_asteroid = false)

        neoRepository.switchHazardous(neo)

        verify(localDataSource).save(argThat { get(0).is_potentially_hazardous_asteroid })
    }

    @Test
    fun `Switch neoSaved from true to false`(): Unit = runBlocking {
        val neo = fakeNeo.copy(is_potentially_hazardous_asteroid = true)

        neoRepository.switchHazardous(neo)

        verify(localDataSource).save(argThat { !get(0).is_potentially_hazardous_asteroid })
    }
}
