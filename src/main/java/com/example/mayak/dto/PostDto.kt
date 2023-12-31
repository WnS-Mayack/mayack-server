package com.example.mayak.dto

import com.example.mayak.entity.Comment
import com.example.mayak.entity.ItemStatus
import com.example.mayak.entity.Post
import java.time.LocalDateTime
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

data class PostDto(
        var id: Long,
        var description: String,
        var imagePath: String,
        var price: Int,
        var region: String,
        var title: String,
        var modifyAt: LocalDateTime,
        var author: UserDto,
        var comments: MutableList<CommentDto>? = mutableListOf(),
        var itemStatus: ItemStatus,
        var likeCount: Int? = 0,
        var seenCount: Int? = 0
) {
    companion object {
        fun fromPostForMyPage(post: Post): PostDto {
            return PostDto(
                    id = post.id,
                    description = post.description,
                    imagePath = post.imagePath,
                    price = post.price,
                    region = post.region,
                    title = post.title,
                    modifyAt = post.modifyAt,
                    author = UserDto.from(post.user),
                    itemStatus = post.itemStatus,
            )
        }

        fun from(post: Post, likeCount: Int, seenCount: Int): PostDto {
            return PostDto(
                    id = post.id,
                    description = post.description,
                    imagePath = post.imagePath,
                    price = post.price,
                    region = post.region,
                    title = post.title,
                    modifyAt = post.modifyAt,
                    author = UserDto.from(post.user),
                    comments = post.comments.map { CommentDto.from(it) }.toMutableList(),
                    itemStatus = post.itemStatus,
                    likeCount = likeCount,
                    seenCount = seenCount
            )
        }
    }
}