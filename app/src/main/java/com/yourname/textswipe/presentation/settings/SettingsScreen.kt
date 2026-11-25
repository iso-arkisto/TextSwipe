package com.yourname.textswipe.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yourname.textswipe.R
import com.yourname.textswipe.domain.model.AppLanguage
import com.yourname.textswipe.domain.model.ThemeMode
import com.yourname.textswipe.presentation.settings.components.OptionGroup
import com.yourname.textswipe.presentation.settings.components.SettingsSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val appLanguage by viewModel.appLanguage.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.settings)) }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SettingsSection(title = stringResource(R.string.theme)) {
                OptionGroup(
                    options = ThemeMode.entries,
                    selected = themeMode,
                    label = { context.getString(it.titleRes) },
                    onSelect = { viewModel.onThemeSelected(it) }
                )
            }

            SettingsSection(title = stringResource(R.string.language)) {
                OptionGroup(
                    options = AppLanguage.entries,
                    selected = appLanguage,
                    label = { it.title },
                    onSelect = { viewModel.onLanguageSelected(it) }
                )
            }
        }
    }
}