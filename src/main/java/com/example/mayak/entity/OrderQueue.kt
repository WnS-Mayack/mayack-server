package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class OrderQueue(
        user: User,
        post: Post
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