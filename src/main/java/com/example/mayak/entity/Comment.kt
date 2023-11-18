package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class Comment(
        post: Post,
        user: User,
        content: String
) : Auditing() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0L

    @ManyToOne
    var post: Post = post

    @ManyToOne
    var user: User = user

    var content = content
}