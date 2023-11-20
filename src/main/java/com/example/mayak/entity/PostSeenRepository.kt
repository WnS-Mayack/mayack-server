package com.example.mayak.entity;

import org.springframework.data.jpa.repository.JpaRepository

interface PostSeenRepository : JpaRepository<PostSeen, Long> {
}