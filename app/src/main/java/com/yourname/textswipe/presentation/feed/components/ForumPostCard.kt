package com.yourname.textswipe.presentation.feed.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.yourname.textswipe.domain.model.FeedItem
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import com.yourname.textswipe.utils.toComposeColorOrNull
import com.yourname.textswipe.utils.toDateTimeString

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ForumPostCard(
    feedItem: FeedItem.ForumPost,
    onSwiped: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val swipeThreshold = screenWidth * 0.35f

    var showGallery by remember { mutableStateOf(false) }

    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.65f)
            .aspectRatio(0.7f)
            .offset { IntOffset(offset.value.x.roundToInt(), offset.value.y.roundToInt()) }
            .graphicsLayer {
                rotationZ = (offset.value.x / 20f).coerceIn(-15f, 15f)
                alpha = 1f - (abs(offset.value.x) / screenWidth).coerceIn(0f, 0.4f)
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            if (abs(offset.value.x) > swipeThreshold) {
                                val targetX = if (offset.value.x > 0) screenWidth else -screenWidth
                                offset.animateTo(
                                    targetValue = Offset(targetX, offset.value.y),
                                    animationSpec = tween(durationMillis = 300)
                                )
                                onSwiped()
                            } else {
                                offset.animateTo(Offset.Zero, tween(300))
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            offset.snapTo(Offset(offset.value.x + dragAmount.x, offset.value.y + dragAmount.y))
                        }
                    }
                )
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = feedItem.author.avatarUrl,
                    contentDescription = "Avatar ${feedItem.author.username}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = feedItem.author.username,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = feedItem.publishedAt.toDateTimeString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = feedItem.platform.colorHex.toComposeColorOrNull()
                        ?: MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "${feedItem.platform.name} • ${feedItem.platformCategory.name}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (feedItem.imageUrls.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { showGallery = true }
                ) {
                    AsyncImage(
                        model = feedItem.imageUrls.first(),
                        contentDescription = "Preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    if (feedItem.imageUrls.size > 1) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Black.copy(alpha = 0.7f),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "📸 1/${feedItem.imageUrls.size}",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }



            feedItem.title?.let { title ->
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = feedItem.text,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                )

                if (feedItem.sources.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        feedItem.sources.forEach { source ->
                            SuggestionChip(
                                onClick = { uriHandler.openUri(source.link) },
                                label = {
                                    Text(
                                        text = "🔗 ${source.title}",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (feedItem.tags.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    feedItem.tags.forEach { tag ->
                        Text(
                            text = "#$tag",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                }
            }


        }
    }

    if (showGallery && feedItem.imageUrls.isNotEmpty()) {
        FullscreenGalleryDialog(
            imageUrls = feedItem.imageUrls,
            onDismissRequest = { showGallery = false }
        )
    }
}