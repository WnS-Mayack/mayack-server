package com.example.mayak.requests

data class PostRequest(
        var description: String,
        var imagePath: String,
        var price: Int,
        var region: String,
        var title: String
)