package com.yourname.textswipe.data.repository

import com.yourname.textswipe.domain.model.Category
import com.yourname.textswipe.domain.model.DefaultAuthors
import com.yourname.textswipe.domain.model.DefaultCategories
import com.yourname.textswipe.domain.model.DefaultPlatformCategories
import com.yourname.textswipe.domain.model.DefaultPlatformCategories.FML_VACATION
import com.yourname.textswipe.domain.model.DefaultPlatformCategories.PIKABU_TRANSPORT
import com.yourname.textswipe.domain.model.DefaultPlatformCategories.REDDIT_TIFU
import com.yourname.textswipe.domain.model.DefaultPlatforms
import com.yourname.textswipe.domain.model.FeedItem
import com.yourname.textswipe.domain.model.ForumUser
import com.yourname.textswipe.domain.model.SourceLink
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
        ),
        FeedItem.ForumPost(
            tags = listOf("pitbike","situation","no_accident","idiocy","inadequacy","impudence","car","rudeness","text"),
            category = DefaultCategories.STORIES,
            platformCategory = PIKABU_TRANSPORT,
            title = "Every driver wants to know",
            text = "Until yesterday, pit bikers were nothing more than an annoying nighttime grunt to me. Uncomfortable, sure, but fixable: just close the window and go back to sleep. But boy, did I get the full experience.\n\nWe hopped on a suburban train out of town for my niece’s birthday party. My sister-in-law picked us up from the station—she’s a careful, highly cautious driver. It’s a fifteen-minute drive to the summer house, a predictable road we’ve driven or ridden on a hundred times before.\n\nThen came the turn toward the vacation community—and there was our little surprise. Darting out from the local grocery store on our right was this blonde wonder with a broccoli haircut. He started fishtailing his rear wheel right in front of our hood, looked back, smirked, and veered all the way across to the far-left shoulder. Then he swung back, cut off a crawling tractor the exact same way, and blasted into the distance.\n\nI’m sure the brave lad imagined himself as a Rebel fighter fleeing Imperial stormtroopers on a speeder bike. My usually quiet, reserved sister-in-law was swearing like a sailor. I was practically biting my knee to keep from letting out a torrent of profanity. My only regret was that my phone was sitting peacefully in my pocket. It’s always like that: the one time you actually need to document something, your hands are empty.\n\nFrom this moment on, I am officially joining the outraged chorus.",
            author = ForumUser(
                username = "Lepenson",
                avatarUrl = "https://cs19.pikabu.ru/s/2026/01/19/13/ayaewqwi_s.webp"
            ),
            publishedAt = 1786361520000,
            platform = DefaultPlatforms.PIKABU,
            imageUrls = emptyList(),
            sources = listOf(SourceLink(
                title = "Pikabu",
                link = "https://pikabu.ru/story/kazhdyiy_voditel_zhelaet_znat_14230146"
            ))
        ),
        FeedItem.ForumPost(
            tags = listOf("passengers","public_transport","transport","dushanbe","bus","longread"),
            category = DefaultCategories.STORIES,
            platformCategory = PIKABU_TRANSPORT,
            title = "Transportation in Dushanbe",
            text = "Today I suggest talking about transport in Dushanbe \uD83D\uDE95\uD83D\uDE8C\n\nWalking around the city, you will probably notice the green electric vehicles, of which there are truly many on the roads here. This is the local taxi.\nAccording to my observations, about 30% of all traffic in Dushanbe comes from taxis.\n\nOne of the reasons for such popularity is the price.\nFor example, a trip over a distance of about five public transport stops will cost only 10 somoni (≈ 90 ₽).\n\nWith prices like these, you pretty quickly start to wonder: do you even need a bus at all? \uD83D\uDE04 \n \nSpeaking of buses \uD83D\uDE8C\n\nDushanbe's bus fleet is quite diverse: you can see brand new buses with air conditioning running around the city, as well as models that have clearly seen a lot of passengers in their lifetime \uD83D\uDE04\n\nAt the same time, regardless of the vehicle's age, it is usually quite clean and tidy inside.\n\nAnd now, dynamic pricing attention — the fare cost.\n\nA ride on a bus or trolleybus will cost just:2.5 somoni (≈ 22.5 ₽) \uD83D\uDE33\n\nMoreover, this is the trip cost regardless of whether you rode a couple of stops or decided to take yourself on a mini-tour around Dushanbe.\n\nThe question \"do you even need a bus at all?\" seems to answer itself \uD83D\uDE04 \n\nSpeaking of police cars in Dushanbe, they deserve a special mention \uD83D\uDE94\n\nThe vehicles are always clean and well-maintained, and often quite new too.\n\nTo be honest, it is much easier to spot a police car here that looks like it just rolled out of a car wash than a dirty one \uD83D\uDE42 \n\nLocal medics are not lagging behind either \uD83D\uDE91\n\nThey move around the city in vehicles like these.\n\nAnd the same trend holds true here: the vehicles are clean, well-maintained, and look very decent.",
            author = ForumUser(
                username = "Dmitriy.Konstant",
                avatarUrl = "https://cs18.pikabu.ru/s/2025/12/12/14/imprch3e_s.webp"
            ),
            publishedAt = 1786444740000,
            platform = DefaultPlatforms.PIKABU,
            imageUrls = listOf(
                "https://cs16.pikabu.ru/s/2026/08/11/13/a56x3vhb.jpg",
                "https://cs20.pikabu.ru/s/2026/08/11/13/2gccp4qw.jpg",
                "https://cs18.pikabu.ru/s/2026/08/11/13/2kcbfcii.jpg",
                "https://cs17.pikabu.ru/s/2026/08/11/13/xgeewqhe.jpg"
            ),
            sources = listOf(SourceLink(
                title = "Pikabu",
                link = "https://pikabu.ru/story/transport_v_dushanbe_14233103"
            ))
        ),
        FeedItem.ForumPost(
            tags = listOf("languagelearning","collegelife","humor","romance"),
            category = DefaultCategories.STORIES,
            platformCategory = REDDIT_TIFU,
            title = "When I learned a language to impress a girl.",
            text = "Years ago, back in college, I was studying computer science. As part of my degree, I needed to take a foreign language course, so I originally signed up for Spanish.\n\nAround the same time, I had a business services class. Sitting right next to me was a gorgeous international student. I quickly noticed she didn't speak much English; she had a friend next to her who translated everything the professor said. I tried to say hello once, but the friend politely told me, \"Sorry, she doesn’t speak English.\" Before I could respond, the lecture started.\n\nRight after class, I overheard her friend talking to someone else, saying, \"Oh nice, I speak Chinese too!\" My brain immediately connected the dots. I thought: “If I learn her language, I can finally talk to her and impress her.” Since it was still the course add/drop period, I dropped Spanish and enrolled in Mandarin Chinese.\n\nIt was incredibly tough. Chinese is not an easy language to master, but I studied hard every single day, motivated by the thought of our future conversation. I decided to wait until the very last day of the semester to approach her, wanting my skills to be good enough for a real chat.\n\nWhen the final day arrived, I walked up to her, nervous but ready. In my best possible Chinese, I managed to say that I thought she was beautiful and that I had spent the whole semester learning the language just to talk to her.\n\nHer friend stared at me in shock, blinked, and then said in English:\"That is incredibly sweet that you learned Chinese for her... but she doesn't speak Chinese. She speaks Japanese. I'm the one who speaks Chinese.\"\n\nI stood there frozen. Turns out, I had spent months struggling through Mandarin characters all because I misheard which friend spoke what language. The girl actually gave me a hug because she found the effort adorable, but that was it. I never saw her again after that semester, but hey—at least now I know some Chinese.",
            author = ForumUser(
                username = "u/pics4meeee",
            ),
            publishedAt = 1708111960000,
            platform = DefaultPlatforms.REDDIT,
            sources = listOf(SourceLink(
                title = "r/tifu",
                link = "https://www.reddit.com/r/tifu/comments/1ashcpm/tifu_when_i_learned_a_language_to_impress_a_girl/"
            ))
        ),
        FeedItem.ForumPost(
            tags = listOf("travelstories","holidayvibe","vacation","awkward","hotel_life"),
            category = DefaultCategories.STORIES,
            platformCategory = FML_VACATION,
            title = "Run Hide Fright",
            text = "Today, my husband and I are on holiday in Rome, Italy. Yesterday, we made quick chit-chat with an older couple who are staying at the same hotel as us. Big mistake. Now, they are trying to spend every day with us, even following us to the exact same cafes and museums. We’ve started actively avoiding them and literally hiding in our room. The worst part? We're here for another week. Wish us luck!",
            author = ForumUser(
                username = "IjustwanttoenjoyRome"
            ),
            publishedAt = 1786568400000,
            platform = DefaultPlatforms.FML,
            sources = listOf(SourceLink(
                title = "FML",
                link = "https://www.fmylife.com/article/run-hide-fright_512522.html"
            ))
        )
    )

    override suspend fun getFeedItems(): List<FeedItem> {
        return feedItems.shuffled()
    }
}