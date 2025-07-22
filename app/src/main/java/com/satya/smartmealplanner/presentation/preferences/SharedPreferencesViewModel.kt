package com.satya.smartmealplanner.presentation.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.smartmealplanner.domain.preferences.GetStringPreferenceUseCase
import com.satya.smartmealplanner.domain.preferences.SaveStringPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedPreferencesViewModel @Inject constructor(
    private val saveStringPreferenceUseCase: SaveStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase
) : ViewModel() {

    fun saveCurrentDate(date: String) {
        viewModelScope.launch {
            saveStringPreferenceUseCase("current_date", date)
        }
    }

    fun getCurrentDate(onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val date = getStringPreferenceUseCase("current_date", "")
            onResult(date)
        }
    }

}