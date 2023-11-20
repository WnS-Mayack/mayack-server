package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class PostLike(
        post: Post,
        user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne
    var user: User = user

    @ManyToOne
    var post: Post = post
}