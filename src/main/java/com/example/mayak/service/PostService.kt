package com.example.mayak.service

import com.example.mayak.Repository.PostRepository
import com.example.mayak.dto.PostDto
import com.example.mayak.entity.Post
import com.example.mayak.requests.PostRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
        private val postRespository: PostRepository
) {

    fun get(postId: Long) {

    }

    @Transactional(readOnly = true)
    fun getAll(): List<PostDto> {
        val posts = postRespository.findAll()
        posts.sortByDescending { it.id }
        return posts.map {
            PostDto.from(it)
        }.toList()
    }

    @Transactional
    fun create(request: PostRequest) {
        val post = Post(
                description = request.description,
                imagePath = request.imagePath,
                price = request.price,
                region = request.region,
                title = request.title
        )
        postRespository.save(post)
    }

}
