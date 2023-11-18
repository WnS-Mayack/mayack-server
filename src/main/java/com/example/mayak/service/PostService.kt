package com.example.mayak.service

import com.example.mayak.Repository.PostRepository
import com.example.mayak.dto.PostDto
import com.example.mayak.entity.Post
import com.example.mayak.entity.QPost
import com.example.mayak.entity.QUser
import com.example.mayak.requests.PostRequest
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
        private val postRespository: PostRepository,
        private val queryFactory: JPAQueryFactory,
) {

    @Transactional(readOnly = true)
    fun get(postId: Long): PostDto {
        val post = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.id.eq(postId))
                .fetchOne() ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다. id : $postId")
        return PostDto.from(post)
    }

    @Transactional(readOnly = true)
    fun getAll(): List<PostDto> {
        val posts = postRespository.findAll()
        posts.sortByDescending { it.id }
        return posts.map {
            PostDto.from(it)
        }.toList()
    }

    @Transactional
    fun create(request: PostRequest, headers: HttpHeaders) {
        val header = headers.get("account") ?: throw IllegalArgumentException("헤더가 존재 하지 않음.")
        val account = header.get(0)
        val user = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne() ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
        val post = Post(
                description = request.description,
                imagePath = request.imagePath,
                price = request.price,
                region = request.region,
                title = request.title,
                user = user
        )
        postRespository.save(post)
    }

}
