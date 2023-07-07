package com.alvarez.sergio.actraining.data.datasource

import com.alvarez.sergio.actraining.domain.NeoEntityDomain

interface NeoRemoteDataSource {
    suspend fun fetchNeos(): List<NeoEntityDomain>
}
