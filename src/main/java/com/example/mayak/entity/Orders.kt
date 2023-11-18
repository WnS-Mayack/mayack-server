package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class Orders(
        user: User,
        post: Post
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @OneToOne
    var user: User = user

    @OneToOne
    var post: Post = post
}