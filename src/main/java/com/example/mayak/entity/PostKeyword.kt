package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class PostKeyword(
        user: User,
        keyword: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne
    var user: User = user

    var keyword = keyword

}