package com.example.mayak.controller

import com.example.mayak.dto.PostKeywordDto
import com.example.mayak.dto.UserDto
import com.example.mayak.entity.QUser
import com.example.mayak.requests.LoginRequest
import com.example.mayak.requests.PostKeywordRequest
import com.example.mayak.requests.SignUpRequest
import com.example.mayak.service.UserService
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
        private val userService: UserService,
        private val queryFactory: JPAQueryFactory
) {

    @PostMapping("/login")
    fun login(
            @RequestBody loginRequest: LoginRequest
    ) {
        userService.login(loginRequest)
    }

    @PostMapping("/sign-up")
    fun signUp(
            @RequestBody loginRequest: SignUpRequest
    ) {

        val account = loginRequest.account

        val existUser = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne()

        if (existUser != null) throw IllegalArgumentException("user id가 중복되었습니다.")

        userService.signUp(loginRequest)
    }

    @GetMapping("/keywords")
    fun getKeywords(
            @RequestHeader headers: HttpHeaders
    ): PostKeywordDto {
        return userService.getKeywords(headers)
    }

    @PostMapping("/keywords")
    fun createKeywords(
            @RequestBody keywords: PostKeywordRequest,
            @RequestHeader headers: HttpHeaders
    ) {
        return userService.createKeywords(keywords, headers)
    }

    @GetMapping()
    fun get(@RequestHeader headers: HttpHeaders
    ): UserDto {
       return userService.get(headers)
    }

    @GetMapping("/sellItems")
    fun getSellItems(@RequestHeader headers: HttpHeaders
    ) {

    }

    @GetMapping("/buyItems")
    fun getBuyItems(@RequestHeader headers: HttpHeaders
    ) {

    }

    @GetMapping("/likeItmes")
    fun getLikeItems(@RequestHeader headers: HttpHeaders
    ) {

    }
}