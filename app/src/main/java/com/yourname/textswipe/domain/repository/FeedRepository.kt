package com.yourname.textswipe.domain.repository

import com.yourname.textswipe.domain.model.FeedItem

interface FeedRepository {
    suspend fun getFeedItems(firstItemId: String?): List<FeedItem>
    suspend fun getItemById(itemId: String): FeedItem?
}