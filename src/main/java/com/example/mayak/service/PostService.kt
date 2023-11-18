package com.example.mayak.service

import com.example.mayak.Repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
        private val postRespository: PostRepository
) {

    fun get(postId: Long) {

    }

    fun getAll() {

    }

    @Transactional
    fun create() {

    }

}
