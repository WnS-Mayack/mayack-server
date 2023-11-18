package com.example.mayak.Repository

import com.example.mayak.entity.OrderQueue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderQueueRepository :JpaRepository<OrderQueue, Long>{

}
