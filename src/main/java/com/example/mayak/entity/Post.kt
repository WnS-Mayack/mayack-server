package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class Post(
        description: String,
        imagePath: String,
        price: Int,
        region: String,
        title: String,
        user: User
) : Auditing() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0L

    var description = description

    var imagePath = imagePath

    var price = price

    var region = region

    var title = title

    @ManyToOne
    var user: User = user

    @OneToMany(mappedBy = "post")
    var comments: MutableList<Comment> = mutableListOf()
}