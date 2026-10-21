package com.yourname.textswipe.presentation.feed.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.textswipe.domain.model.FeedItem

const val MAX_CENTERED_TEXT_LENGTH = 150

@Composable
fun TextCard(feedItem: FeedItem.Text) {
    var isExpanded by remember { mutableStateOf(false) }
    var showExpandArrow by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = feedItem.category.name,
            style = if(feedItem.title != null) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 25.sp
        )

        feedItem.title?.let { title ->

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                lineHeight = 44.sp,
                textAlign = TextAlign.Center
            )

        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = feedItem.content,
                fontSize = 25.sp,
                textAlign = if(feedItem.content.length < MAX_CENTERED_TEXT_LENGTH) TextAlign.Center else TextAlign.Left,
                maxLines = if (isExpanded) Int.MAX_VALUE else 10,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    if (textLayoutResult.hasVisualOverflow && !isExpanded) {
                        showExpandArrow = true
                    }
                },
                modifier = Modifier.then(
                    if (isExpanded) Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState())
                    else Modifier
                ),
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 31.sp
            )

            if (showExpandArrow || isExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}