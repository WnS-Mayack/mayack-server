package com.example.mayak.controller

import com.example.mayak.entity.PostKeyword
import com.example.mayak.requests.LoginRequest
import com.example.mayak.requests.SignUpRequest
import com.example.mayak.service.UserService
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
        private val userService: UserService
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
        userService.signUp(loginRequest)
    }

    @GetMapping("/keywords")
    fun getKeywords(
            @RequestHeader headers: HttpHeaders
    ): List<String> {
        return userService.getKeywords(headers)
    }
}