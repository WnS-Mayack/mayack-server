package com.example.mayak.entity

import jakarta.persistence.*

@Entity
class User(
        region: String,
        account: String,
        password: String,
        profileImgPath: String?,
        nickname: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var region: String = region
        protected set

    @Column(unique = true)
    var account: String = account
        protected set

    var password = password
        protected set

    var profileImgPath: String? = profileImgPath
        protected set

    @Column(unique = true)
    var nickname = nickname
        protected set


}
