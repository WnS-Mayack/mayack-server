package com.example.mayak.Repository

import com.example.mayak.entity.BuyOrders
import com.example.mayak.entity.SellOrders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BuyOrderRepository : JpaRepository<BuyOrders, Long> {

}
