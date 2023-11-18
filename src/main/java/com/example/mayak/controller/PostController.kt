package com.example.mayak.controller

import com.example.mayak.service.PostService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(
        private val postService: PostService
) {

    @GetMapping("/{id}")
    fun get(
            @PathVariable("id") postId: Long
    ) {
        postService.get(postId)
    }

    @GetMapping()
    fun getAll() {
        postService.getAll()
    }

    @PostMapping
    fun create() {
        postService.create()
    }
}