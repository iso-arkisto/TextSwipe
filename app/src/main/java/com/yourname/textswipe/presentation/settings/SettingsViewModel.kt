package com.yourname.textswipe.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.textswipe.domain.model.AppLanguage
import com.yourname.textswipe.domain.model.ThemeMode
import com.yourname.textswipe.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val themeMode: StateFlow<ThemeMode> = settingsRepository.themeMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeMode.System
        )

    val appLanguage: StateFlow<AppLanguage> = settingsRepository.appLanguage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppLanguage.System
        )

    fun onThemeSelected(theme: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.setTheme(theme)
        }
    }

    fun onLanguageSelected(language: AppLanguage) {
        viewModelScope.launch {
            settingsRepository.setLanguage(language)
        }
    }
}