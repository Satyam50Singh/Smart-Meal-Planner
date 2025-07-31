package com.satya.smartmealplanner.domain.preferences

import javax.inject.Inject

class GetStringPreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(key: String, defaultValue: String): String {
        return repository.getString(key, defaultValue)
    }
}