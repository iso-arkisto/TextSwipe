package com.yourname.textswipe.presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.textswipe.domain.model.FeedItem
import com.yourname.textswipe.presentation.feed.components.ForumPostCard
import com.yourname.textswipe.presentation.feed.components.QuoteCard
import com.yourname.textswipe.presentation.feed.components.TextCard

@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is FeedUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            when(val state = uiState) {
                is FeedUiState.Loading -> CircularProgressIndicator()
                is FeedUiState.Success -> {
                    val feedItems = state.feedItems

                    feedItems.take(2).reversed().forEach { item ->
                        key(item.id) {
                            when(item) {
                                is FeedItem.Text -> {
                                TextCard(
                                    feedItem = item,
                                    onSwiped = viewModel::onItemSwiped
                                )
                                }
                                is FeedItem.Quote -> {
                                    QuoteCard(
                                        feedItem = item,
                                        onSwiped = viewModel::onItemSwiped
                                    )
                                }
                                is FeedItem.ForumPost -> {
                                    ForumPostCard(
                                        feedItem = item,
                                        onSwiped = viewModel::onItemSwiped
                                    )
                                }
                            }
                        }
                    }

                    feedItems.firstOrNull()?.let {
                        IconButton(
                            onClick = viewModel::toggleBookmark,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = if(isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Save to bookmarks",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                is FeedUiState.Error -> Text("Something went wrong")
            }
        }
    }
}