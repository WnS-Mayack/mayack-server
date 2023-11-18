package com.example.mayak.controller

import com.example.mayak.requests.CommentRequest
import com.example.mayak.service.CommentService
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/comments")
class CommentController(
        private val commentService: CommentService
) {
    @PostMapping()
    fun create(
            @RequestHeader headers: HttpHeaders,
            @RequestBody commentRequest: CommentRequest
    ) {
        commentService.create(headers, commentRequest)
    }

}