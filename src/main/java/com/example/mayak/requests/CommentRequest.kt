package com.example.mayak.requests

data class CommentRequest(
        var postId: Long,
        var content: String,
)
