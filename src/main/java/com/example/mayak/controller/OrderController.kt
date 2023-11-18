package com.example.mayak.controller

import com.example.mayak.dto.OrderQueueDto
import com.example.mayak.service.OrderService
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
        private val orderService: OrderService
) {
    //주문 queue에 쌓는다.
    @PostMapping("/{postId}/buy")
    fun buyItem(
            @PathVariable("postId") postId: Long,
            @RequestHeader headers: HttpHeaders
    ) {
        orderService.buyItem(postId, headers)
    }

    // 해당 상품에 대한 구매 요청 목록을 조회한다.
    @GetMapping("/{postId}/buy")
    fun getBuyOrders(@PathVariable("postId") postId: Long): OrderQueueDto {
        return orderService.getOrdersQueue(postId)
    }

    // 판매 확정.
    @PostMapping("/{postId}/sell/{customerId}")
    fun sellItem(
            @PathVariable("postId") postId: Long,
            @PathVariable("customerId") customerId: Long,
            @RequestHeader headers: HttpHeaders
    ) {
        orderService.sellItem(headers, postId, customerId)
    }

}
