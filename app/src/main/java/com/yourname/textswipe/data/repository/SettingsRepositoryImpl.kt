package com.yourname.textswipe.data.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.yourname.textswipe.data.local.settings.SettingsManager
import com.yourname.textswipe.domain.model.AppLanguage
import com.yourname.textswipe.domain.model.ThemeMode
import com.yourname.textswipe.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsManager: SettingsManager
) : SettingsRepository {

    override val themeMode: Flow<ThemeMode> = settingsManager.themeMode
    override val appLanguage: Flow<AppLanguage> = settingsManager.language

    override suspend fun setTheme(theme: ThemeMode) {
        settingsManager.setThemeMode(theme)

        AppCompatDelegate.setDefaultNightMode(
            when (theme) {
                ThemeMode.System -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                ThemeMode.Light -> AppCompatDelegate.MODE_NIGHT_NO
                ThemeMode.Dark -> AppCompatDelegate.MODE_NIGHT_YES
            }
        )
    }

    override suspend fun setLanguage(language: AppLanguage) {
        settingsManager.setLanguage(language)

        val locales = if (language.tag.isEmpty()) {
            LocaleListCompat.getEmptyLocaleList()
        } else {
            LocaleListCompat.forLanguageTags(language.tag)
        }
        AppCompatDelegate.setApplicationLocales(locales)
    }
}