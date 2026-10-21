package com.yourname.textswipe.domain.usecase

import com.yourname.textswipe.domain.model.FeedItem
import com.yourname.textswipe.domain.repository.FeedRepository
import javax.inject.Inject

class GetItemByIdUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(itemId: String): Result<FeedItem> {
        return try {
            val item = repository.getItemById(itemId)

            if(item != null) {
                Result.success(item)
            } else {
                throw NoSuchElementException("Item not found")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}