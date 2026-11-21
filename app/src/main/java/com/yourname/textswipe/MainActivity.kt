package com.yourname.textswipe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yourname.textswipe.presentation.main.MainScreen
import com.yourname.textswipe.presentation.settings.SettingsViewModel
import com.yourname.textswipe.ui.theme.TextSwipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by settingsViewModel.themeMode.collectAsStateWithLifecycle()

            TextSwipeTheme(themeMode) {
                MainScreen()
            }
        }
    }
}