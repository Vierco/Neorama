package com.alvarez.sergio.actraining.data.database

import com.alvarez.sergio.actraining.data.datasource.NeoLocalDataSource
import com.alvarez.sergio.actraining.domain.CustomError
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import extensions.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NeoRoomDataSource @Inject constructor(private val neoDAO: NeoDAO) : NeoLocalDataSource {
    override val neos: Flow<List<NeoEntityDomain>> = neoDAO.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = neoDAO.newsCount() == 0

    override fun findById(id: Int): Flow<NeoEntityDomain> = neoDAO.findById(id).map { it.toDomainModel() }

    override suspend fun save(neos: List<NeoEntityDomain>): CustomError? = tryCall {
        neoDAO.insertNeos(neos.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<NeoEntity>.toDomainModel(): List<NeoEntityDomain> = map { it.toDomainModel() }
private fun NeoEntity.toDomainModel(): NeoEntityDomain =
    NeoEntityDomain(
        id,
        name,
        absolute_magnitude_h,
        imageAssigned,
        is_potentially_hazardous_asteroid,
        nasa_jpl_url,
        is_sentry_object,
        neo_reference_id
    )

private fun List<NeoEntityDomain>.fromDomainModel(): List<NeoEntity> = map { it.fromDomainModel() }
private fun NeoEntityDomain.fromDomainModel(): NeoEntity = NeoEntity(
    id,
    name,
    absolute_magnitude_h,
    imageAssigned,
    is_potentially_hazardous_asteroid,
    nasa_jpl_url,
    is_sentry_object,
    neo_reference_id
)
