package com.example.mayak.private

import jakarta.persistence.*

@Entity
class Tag(
        name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    val name: String = name

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag", cascade = [CascadeType.ALL])
//    var posts: MutableList<PostTag> = mutableListOf()
}