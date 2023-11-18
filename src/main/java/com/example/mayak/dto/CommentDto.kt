package com.example.mayak.dto

import com.example.mayak.entity.Comment
import com.example.mayak.entity.Post
import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link com.example.mayak.entity.Comment}
 */
data class CommentDto(
        val modifyAt: LocalDateTime,
        val id: Long,
        val author: UserDto,
        val content: String
) {
    companion object {
        fun from(comment: Comment): CommentDto {
            return CommentDto(
                    id = comment.id,
                    modifyAt = comment.modifyAt,
                    author = UserDto.from(comment.user),
                    content = comment.content
            )
        }
    }
}