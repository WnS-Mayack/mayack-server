package com.example.mayak.dto

import com.example.mayak.entity.OrderQueue

data class OrderQueueDto(
        var users: MutableList<UserDto>
) {
    companion object {
        fun from(orderQueues: List<OrderQueue>): OrderQueueDto {
            return OrderQueueDto(
                    users = orderQueues.map { UserDto.from(it.user) }.toMutableList()
            )
        }
    }
}
