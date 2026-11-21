package com.yourname.textswipe.domain.repository

import com.yourname.textswipe.domain.model.AppLanguage
import com.yourname.textswipe.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val themeMode: Flow<ThemeMode>
    val appLanguage: Flow<AppLanguage>

    suspend fun setTheme(theme: ThemeMode)

    suspend fun setLanguage(language: AppLanguage)
}