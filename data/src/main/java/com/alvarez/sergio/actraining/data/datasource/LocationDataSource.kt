package com.alvarez.sergio.actraining.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
