package com.example.mayak.dto

import com.example.mayak.entity.PostKeyword

data class PostKeywordDto(
        var keywords: MutableList<String>
) {
    companion object {
        fun from(keywords: List<String>): PostKeywordDto {
            return PostKeywordDto(
                    keywords = keywords.toMutableList()
            )
        }
    }
}
