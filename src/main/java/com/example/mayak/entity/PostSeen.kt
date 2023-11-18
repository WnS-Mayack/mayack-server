package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class PostSeen(
        jSessionId: String,
        post: Post
) : Auditing() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne
    var post: Post = post

    var jSessionId: String = jSessionId
}