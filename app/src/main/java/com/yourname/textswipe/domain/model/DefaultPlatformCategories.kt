package com.yourname.textswipe.domain.model

object DefaultPlatformCategories {
    val PIKABU_TRANSPORT = PlatformCategory(
        platformId = DefaultPlatforms.PIKABU.id,
        name = "Transport",
        description = "Cars, motorcycles, planes, and trains—everything that moves. Reviews, impressions, and interesting facts."
    )

    val FML_VACATION = PlatformCategory(
        platformId = DefaultPlatforms.FML.id,
        name = "Vacation",
    )

    val REDDIT_TIFU = PlatformCategory(
        platformId = DefaultPlatforms.REDDIT.id,
        name = "TIFU"
    )
}