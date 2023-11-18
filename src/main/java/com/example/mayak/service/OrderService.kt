package com.example.mayak.service

import com.example.mayak.Repository.OrderQueueRepository
import com.example.mayak.dto.OrderQueueDto
import com.example.mayak.entity.OrderQueue
import com.example.mayak.entity.QOrderQueue
import com.example.mayak.entity.QPost
import com.example.mayak.entity.QUser
import com.example.mayak.utils.HttpHeadersParser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
        private val queryFactory: JPAQueryFactory,
        private val orderQueueRepository: OrderQueueRepository,
) {

    @Transactional
    fun buyItem(postId: Long, headers: HttpHeaders) {
        val account = HttpHeadersParser.getAccount(headers)
        val user = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne() ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다. id : $account")

        val post = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.id.eq(postId))
                .fetchOne() ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다. id : $postId")

        val orderQueue = OrderQueue(
                user = user,
                post = post
        )
        orderQueueRepository.save(orderQueue)
    }

    @Transactional(readOnly = true)
    fun getOrdersQueue(postId: Long): OrderQueueDto {

        val post = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.id.eq(postId))
                .fetchOne() ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다. id : $postId")

        val orderQueues = queryFactory.selectFrom(QOrderQueue.orderQueue)
                .where(QOrderQueue.orderQueue.post.eq(post))
                .fetch()

        return OrderQueueDto.from(orderQueues)
    }

    @Transactional
    fun sellItem(headers: HttpHeaders, postId: Long) {
        val account = HttpHeadersParser.getAccount(headers)
        val user = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne() ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다. id : $account")

        val post = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.id.eq(postId))
                .fetchOne() ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다. id : $postId")

    }

}
