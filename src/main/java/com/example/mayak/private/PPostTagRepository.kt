package com.example.mayak.private

import org.springframework.data.jpa.repository.JpaRepository

interface PPostTagRepository : JpaRepository<PostTag, Long> {
}