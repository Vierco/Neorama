package com.alvarez.sergio.actraining.ui

import com.alvarez.sergio.actraining.data.database.NeoDAO
import com.alvarez.sergio.actraining.data.database.NeoEntity
import com.alvarez.sergio.actraining.data.datasource.LocationDataSource
import com.alvarez.sergio.actraining.data.datasource.NeoRemoteDataSource
import com.alvarez.sergio.actraining.data.repositories.PermissionChecker
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.alvarez.sergio.actraining.testcommonsitems.fakeNeo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

val defaultFakeNeos = listOf(
    fakeNeo.copy(1),
    fakeNeo.copy(2),
    fakeNeo.copy(3),
    fakeNeo.copy(4)
)

class FakeNeoDao(neos: List<NeoEntity> = emptyList()) : NeoDAO {

    private val inMemoryNeos = MutableStateFlow(neos)
    private lateinit var findNeoFlow: MutableStateFlow<NeoEntity>

    override fun getAll(): Flow<List<NeoEntity>> = inMemoryNeos

    override fun findById(id: Int): Flow<NeoEntity> {
        findNeoFlow = MutableStateFlow(inMemoryNeos.value.first { it.id == id })
        return findNeoFlow
    }

    override suspend fun newsCount(): Int = inMemoryNeos.value.size

    override suspend fun insertNeos(Neos: List<NeoEntity>) {
        inMemoryNeos.value = Neos

        if (::findNeoFlow.isInitialized) {
            Neos.firstOrNull() { it.id == findNeoFlow.value.id }
                ?.let { findNeoFlow.value = it }
        }
    }
}

class FakeRemoteDataSource : NeoRemoteDataSource {
    var neos = defaultFakeNeos
    override suspend fun fetchNeos(): List<NeoEntityDomain> = neos
}

class FakeLocationDataSource : LocationDataSource {
    val location = "US"
    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}
