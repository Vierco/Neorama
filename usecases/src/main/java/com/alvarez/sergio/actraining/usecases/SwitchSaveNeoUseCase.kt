package com.alvarez.sergio.actraining.usecases

import com.alvarez.sergio.actraining.data.repositories.NeoRepository
import com.alvarez.sergio.actraining.domain.CustomError
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import javax.inject.Inject

class SwitchSaveNeoUseCase @Inject constructor(private val neoRepository: NeoRepository) {

    suspend operator fun invoke(neo: NeoEntityDomain): CustomError? = neoRepository.switchHazardous(neo)
}
