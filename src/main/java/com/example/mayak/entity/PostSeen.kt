package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class PostSeen(
        jSessionId: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null


    @Column(unique = true)
    var jSessionId: String = jSessionId
}