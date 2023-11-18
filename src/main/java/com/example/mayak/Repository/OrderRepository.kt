package com.example.mayak.Repository

import com.example.mayak.entity.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Orders, Long> {

}
