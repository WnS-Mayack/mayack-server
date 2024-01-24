package com.example.mayak.private

import jakarta.persistence.*

@Entity
class PostTag(
        ppost: PPost,
        tag : Tag
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var ppost: PPost = ppost

    @ManyToOne(fetch = FetchType.LAZY)
    var tag : Tag = tag
}