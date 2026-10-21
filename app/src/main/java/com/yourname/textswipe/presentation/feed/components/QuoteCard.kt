package com.yourname.textswipe.presentation.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yourname.textswipe.domain.model.FeedItem

@Composable
fun QuoteCard(feedItem: FeedItem.Quote) {
    var isExpanded by remember { mutableStateOf(false) }
    var showExpandArrow by remember { mutableStateOf(false) }

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(feedItem.author.imageUrl)
        .setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 13; SM-G998B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Mobile Safari/537.36")
        .crossfade(true)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = feedItem.author.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = feedItem.author.name,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = MaterialTheme.colorScheme.onSurface
    )

    Spacer(modifier = Modifier.height(24.dp))

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.heightIn(max = 200.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "«${feedItem.text}»",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                maxLines = if (isExpanded) Int.MAX_VALUE else 6,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    if (textLayoutResult.hasVisualOverflow && !isExpanded) {
                        showExpandArrow = true
                    }
                },
                modifier = Modifier.then(
                    if (isExpanded) Modifier.verticalScroll(rememberScrollState())
                    else Modifier
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentAlignment = Alignment.Center
    ) {
        if (showExpandArrow || isExpanded) {
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = feedItem.tags.joinToString(separator = " ") { "#$it" },
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        lineHeight = 24.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 18.sp
    )
}