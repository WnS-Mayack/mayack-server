package com.example.mayak.service

import com.example.mayak.Repository.PostRepository
import com.example.mayak.dto.PostDto
import com.example.mayak.entity.*
import com.example.mayak.requests.DefaultFilter
import com.example.mayak.requests.PostRequest
import com.example.mayak.utils.HttpHeadersParser
import com.example.mayak.utils.QueryDslUtils
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
        private val postRespository: PostRepository,
        private val queryFactory: JPAQueryFactory,
        private val postLikeRepository: PostLikeRepository, private val postSeenRepository: PostSeenRepository,
) {

    /**
     * 게시글 데이터를 반환하고, 해당 게시글의 조회 수 를 1 증가한다.
     */
    @Transactional
    fun get(postId: Long, sessionId: String): PostDto {
        val post = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.id.eq(postId))
                .fetchOne() ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다. id : $postId")

        val postLikes = queryFactory.select(QPostLike.postLike)
                .from(QPostLike.postLike)
                .where(QPostLike.postLike.post.eq(post))
                .fetch()

        val likeCount = postLikes.size

        val existSeens = queryFactory.selectFrom(QPostSeen.postSeen)
                .where(QPostSeen.postSeen.post.eq(post),
                        QPostSeen.postSeen.jSessionId.eq(sessionId))
                .fetch()
        if (existSeens.isEmpty()) {
            // 같은 session에서 조회 한 경우 가 없으면, 조회 카운팅.
            val postSeen = PostSeen(
                    jSessionId = sessionId,
                    post = post
            )
            postSeenRepository.save(postSeen)
        }
        val postSeens = queryFactory.selectFrom(QPostSeen.postSeen)
                .where(QPostSeen.postSeen.post.eq(post))
                .fetch()

        val postSeenCount = postSeens.size

        return PostDto.from(post, likeCount, postSeenCount)
    }

    @Transactional(readOnly = true)
    fun getAll(filter: DefaultFilter): List<PostDto> {
        val posts = queryFactory.selectFrom(QPost.post)
                .where(
                        QueryDslUtils.contains(QPost.post.title, filter.title),
                        QueryDslUtils.eq(QPost.post.region, filter.region),
                        QueryDslUtils.between(QPost.post.price, filter.minPrice, filter.maxPrice)
                )
                .orderBy(QPost.post.id.desc())
                .fetch()

        return posts.map {
            val postLikes = queryFactory.select(QPostLike.postLike)
                    .from(QPostLike.postLike)
                    .where(QPostLike.postLike.post.eq(it))
                    .fetch()
            val likeCount = postLikes.size
            val postSeens = queryFactory.selectFrom(QPostSeen.postSeen)
                    .where(QPostSeen.postSeen.post.eq(it))
                    .fetch()

            val postSeenCount = postSeens.size
            PostDto.from(it, likeCount, postSeenCount)
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

    @Transactional
    fun postLike(headers: HttpHeaders, postId: Long) {
        val account = HttpHeadersParser.getAccount(headers)
        val self = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne() ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다. id : $account")

        val post = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.id.eq(postId))
                .fetchOne() ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다. id : $postId")

        val existLike = queryFactory.selectFrom(QPostLike.postLike)
                .where(QPostLike.postLike.user.eq(self),
                        QPostLike.postLike.post.eq(post))
                .fetchOne()

        if (existLike != null) {
            // 이미 좋아요 누른 게시글.
            return postLikeRepository.delete(existLike)
        }
        // 처음 좋아요 누른 로직
        val postLike = PostLike(
                post = post,
                user = self
        )
        postLikeRepository.save(postLike)
    }

}
