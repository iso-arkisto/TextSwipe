package com.yourname.textswipe.domain.repository

import com.yourname.textswipe.domain.model.FeedItem

interface FeedRepository {
    suspend fun getFeedItems(): List<FeedItem>
    suspend fun getItemById(itemId: String): FeedItem?
}