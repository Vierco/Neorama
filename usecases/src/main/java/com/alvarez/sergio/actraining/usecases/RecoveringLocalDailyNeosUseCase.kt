package com.alvarez.sergio.actraining.usecases

import com.alvarez.sergio.actraining.data.repositories.NeoRepository
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecoveringLocalDailyNeosUseCase @Inject constructor(private val neoRepository: NeoRepository) {

    operator fun invoke(): Flow<List<NeoEntityDomain>> = neoRepository.todayNeos
}
