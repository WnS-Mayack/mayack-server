package com.example.mayak.Repository

import com.example.mayak.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository :JpaRepository<Post, Long> {

}
