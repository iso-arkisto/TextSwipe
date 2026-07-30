package com.yourname.textswipe.presentation.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.textswipe.domain.model.FeedItem
import com.yourname.textswipe.presentation.feed.components.TextCard

@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when(val state = uiState) {
            is FeedUiState.Loading -> CircularProgressIndicator()
            is FeedUiState.Success -> {
                val feedItems = state.feedItems

                feedItems.take(2).reversed().forEach { item ->
                    when(item) {
                        is FeedItem.Text -> {
                            TextCard(
                                feedItem = item,
                                onSwiped = viewModel::onItemSwiped
                            )
                        }
                    }
                }
            }
            is FeedUiState.Error -> Text("Something went wrong")
        }
    }
}