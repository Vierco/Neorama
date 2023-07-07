package com.alvarez.sergio.actraining.usecases

import com.alvarez.sergio.actraining.data.repositories.NeoRepository
import com.alvarez.sergio.actraining.domain.CustomError
import javax.inject.Inject

class GetTodayNeosUseCase @Inject constructor(private val neoRepository: NeoRepository) {

    suspend operator fun invoke(): CustomError? = neoRepository.requestTodayNeos()
}
