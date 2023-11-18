package com.example.mayak.dto

import com.example.mayak.entity.Post

data class PostDto(
        var id: Long,
        var description: String,
        var imagePath: String,
        var price: Int,
        var region: String,
        var title: String
) {
    companion object {
        fun from(post: Post): PostDto {
            return PostDto(
                    id = post.id,
                    description = post.description,
                    imagePath = post.imagePath,
                    price = post.price,
                    region = post.region,
                    title = post.title
            )
        }
    }
}