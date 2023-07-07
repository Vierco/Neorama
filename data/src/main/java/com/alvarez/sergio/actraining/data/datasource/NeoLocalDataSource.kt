package com.alvarez.sergio.actraining.data.datasource

import com.alvarez.sergio.actraining.domain.CustomError
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import kotlinx.coroutines.flow.Flow

interface NeoLocalDataSource {
    val neos: Flow<List<NeoEntityDomain>>

    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<NeoEntityDomain>

    suspend fun save(neos: List<NeoEntityDomain>): CustomError?
}
