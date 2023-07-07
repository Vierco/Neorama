package com.alvarez.sergio.actraining.usecases

import com.alvarez.sergio.actraining.data.repositories.NeoRepository
import javax.inject.Inject

class FindNeoByIdUseCase @Inject constructor(private val neoRepository: NeoRepository) {

    operator fun invoke(neoId: Int) = neoRepository.findById(neoId)
}
