package com.alvarez.sergio.actraining.data.repositories

import com.alvarez.sergio.actraining.data.datasource.NeoLocalDataSource
import com.alvarez.sergio.actraining.data.datasource.NeoRemoteDataSource
import com.alvarez.sergio.actraining.domain.CustomError
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import javax.inject.Inject

class NeoRepository @Inject constructor(
    private val localDataSource: NeoLocalDataSource,
    private val remoteDataSource: NeoRemoteDataSource
) {
    val todayNeos = localDataSource.neos

    fun findById(id: Int) = localDataSource.findById(id)

    suspend fun requestTodayNeos(): CustomError? {
        if (localDataSource.isEmpty()) {
            val neos = remoteDataSource.fetchNeos()
            localDataSource.save(neos.map { it })
        }
        return null
    }

    suspend fun switchHazardous(neo: NeoEntityDomain): CustomError? {
        val updateNeo = neo.copy(is_potentially_hazardous_asteroid = !neo.is_potentially_hazardous_asteroid)
        return localDataSource.save(listOf(updateNeo))
    }
}
