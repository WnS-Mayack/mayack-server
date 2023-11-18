package com.example.mayak.service

import com.example.mayak.entity.Comment
import com.example.mayak.entity.CommentRepository
import com.example.mayak.entity.QPost
import com.example.mayak.entity.QUser
import com.example.mayak.requests.CommentRequest
import com.example.mayak.utils.HttpHeadersParser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentService(
        private val queryFactory: JPAQueryFactory, private val commentRepository: CommentRepository
) {
    @Transactional
    fun create(headers: HttpHeaders, request: CommentRequest) {
        val account = HttpHeadersParser.getAccount(headers)
        val user = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne() ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다. id : $account")
        val postId = request.postId
        val post = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.id.eq(postId))
                .fetchOne() ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다. id : $postId")
        val comment = Comment(
                post = post,
                user = user,
                content = request.content
        )
        commentRepository.save(comment)
    }
}
