package com.example.mayak.service

import com.example.mayak.Repository.BuyOrderRepository
import com.example.mayak.Repository.OrderQueueRepository
import com.example.mayak.Repository.SellOrderRepository
import com.example.mayak.dto.OrderQueueDto
import com.example.mayak.entity.*
import com.example.mayak.utils.HttpHeadersParser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
        private val queryFactory: JPAQueryFactory,
        private val orderQueueRepository: OrderQueueRepository,
        private val sellOrderRepository: SellOrderRepository,
        private val buyOrderRepository: BuyOrderRepository,
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

        // 이미 주문 요청 건은 무시한다.
        val existOrderQ = queryFactory.selectFrom(QOrderQueue.orderQueue)
                .where(QOrderQueue.orderQueue.post.eq(post),
                        QOrderQueue.orderQueue.user.eq(user))
                .fetchOne()

        if (existOrderQ == null) {
            // 처음 구매 요청이다.
            val orderQueue = OrderQueue(
                    user = user,
                    post = post
            )
            orderQueueRepository.save(orderQueue)
        }
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
    fun sellItem(headers: HttpHeaders, postId: Long, customerId: Long) {
        val account = HttpHeadersParser.getAccount(headers)
        val self = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne() ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다. id : $account")

        val post = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.id.eq(postId))
                .fetchOne() ?: throw IllegalArgumentException("게시글을 찾을 수 없습니다. id : $postId")
        if (post.itemStatus == ItemStatus.SOLD_OUT) throw IllegalArgumentException("판매 완료된 상품입니다.")
        if (post.user.account != self.account) throw IllegalArgumentException("보안 위협! - 게시글의 주인이 아닙니다. 주인 : ${post.user.account}, 요청 : $account")

        val customer = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.id.eq(customerId))
                .fetchOne() ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다. id : $customerId")


        val orderQueues = queryFactory.selectFrom(QOrderQueue.orderQueue)
                .where(QOrderQueue.orderQueue.post.eq(post))
                .fetch()

        orderQueues.firstOrNull {
            it.user.account == customer.account
        } ?: throw IllegalArgumentException("현재 이 상품을 구매 요청을 한 고객이 아닙니다.")

        val settledSellOrder = SellOrders(
                user = self,
                post = post,
        )
        val settledBuyOrder = BuyOrders(
                user = customer,
                post = post,
        )
        post.sell()
        buyOrderRepository.save(settledBuyOrder)
        sellOrderRepository.save(settledSellOrder)

    }

}
