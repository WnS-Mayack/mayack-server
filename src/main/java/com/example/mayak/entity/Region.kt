package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class Region(
        city: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column
    var city = city
        protected set

}