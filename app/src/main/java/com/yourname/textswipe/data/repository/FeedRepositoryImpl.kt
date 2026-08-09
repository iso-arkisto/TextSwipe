package com.yourname.textswipe.data.repository

import com.yourname.textswipe.domain.model.DefaultAuthors
import com.yourname.textswipe.domain.model.DefaultCategories
import com.yourname.textswipe.domain.model.FeedItem
import com.yourname.textswipe.domain.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor() : FeedRepository {

    private val feedItems = listOf(
        FeedItem.Text(
            title = "Dolphins have names",
            category = DefaultCategories.FACTS,
            content = "They communicate using a unique whistle. Each dolphin develops its own signal as a youngling. Other members of the pod use this whistle to call out to a specific individual."
        ),
        FeedItem.Text(
            title = "Honey never spoils",
            category = DefaultCategories.FACTS,
            content = "Archaeologists have found pots of honey in Egyptian tombs that were over 3,000 years old. The product was completely edible. It has too little moisture and is highly acidic, so bacteria cannot thrive in it."
        ),
        FeedItem.Text(
            category = DefaultCategories.JOKES,
            content = "My grandfather always said, \"If it's hard for you to walk, it means you're climbing.\" He was a wonderful man. But as a mountaineering guide, he was simply terrible."
        ),
        FeedItem.Text(
            category = DefaultCategories.JOKES,
            content = "How does the ocean say hi? It waves!"
        ),
        FeedItem.Quote(
            text = "I know that I know nothing.",
            author = DefaultAuthors.SOCRATES,
            tags = listOf("humility", "wisdom", "awareness")
        ),
        FeedItem.Quote(
            text = "The definition of insanity is doing the same thing over and over and expecting different results.",
            author = DefaultAuthors.ALBERT_EINSTEIN,
            tags = listOf("growth", "choices", "mistakes", "pattern")
        ),
        FeedItem.Quote(
            text = "Wise men talk because they have something to say; fools, because they would like to say something.",
            author = DefaultAuthors.PLATO,
            tags = listOf("wisdom", "silence", "speech")
        )
    )

    override suspend fun getFeedItems(): List<FeedItem> {
        return feedItems.shuffled()
    }
}