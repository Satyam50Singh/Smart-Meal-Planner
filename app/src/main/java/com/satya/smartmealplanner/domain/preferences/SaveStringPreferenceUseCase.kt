package com.satya.smartmealplanner.domain.preferences

import javax.inject.Inject

class SaveStringPreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(key: String, value: String) {
        repository.saveString(key, value)
    }
}