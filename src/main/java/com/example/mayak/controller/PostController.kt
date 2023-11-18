package com.example.mayak.controller

import com.example.mayak.dto.PostDto
import com.example.mayak.requests.DefaultFilter
import com.example.mayak.requests.PostRequest
import com.example.mayak.service.PostService
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(
        private val postService: PostService
) {

    @GetMapping("/{id}")
    fun get(
            @PathVariable("id") postId: Long
    ): PostDto {
        return postService.get(postId)
    }

    @GetMapping()
    fun getAll(
            @RequestParam("title") title: String?,
            @RequestParam("region") region: String,
            @RequestParam("minPrice") minPrice: Int?,
            @RequestParam("maxPrice") maxPrice: Int?,
    ): List<PostDto> {
        val filter = DefaultFilter(
                region = region,
                title = title,
                minPrice = minPrice,
                maxPrice = maxPrice
        )
        return postService.getAll(filter)
    }

    @PostMapping
    fun create(
            @RequestBody postRequest: PostRequest,
            @RequestHeader header: HttpHeaders
    ) {
        postService.create(postRequest, header)
    }
}