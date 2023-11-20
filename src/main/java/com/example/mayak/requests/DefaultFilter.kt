package com.example.mayak.requests

data class DefaultFilter(
        var region: String?,
        var title: String?,
        var minPrice: Int?,
        var maxPrice: Int?
)