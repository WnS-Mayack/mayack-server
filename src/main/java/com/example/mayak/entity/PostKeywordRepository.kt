package com.example.mayak.entity;

import org.springframework.data.jpa.repository.JpaRepository

interface PostKeywordRepository : JpaRepository<PostKeyword, Long> {
}