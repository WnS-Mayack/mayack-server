package com.example.mayak.private

import jakarta.persistence.*


@Entity
@Table(name = "ppost")
class PPost(
        puser : PUser,
        title: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ppost", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var tags: MutableList<PostTag> = mutableListOf()

    @ManyToOne(fetch = FetchType.EAGER)
    var puser : PUser = puser

    var title: String = title
}