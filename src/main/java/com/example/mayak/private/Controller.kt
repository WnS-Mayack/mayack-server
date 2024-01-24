package com.example.mayak.private

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mtm/context")
class Controller(
        val service: Service
) {

    @PostMapping("/post")
    fun create() {
        service.create()

    }

    @DeleteMapping("/post")
    fun del() {
        service.delete()
    }

    @DeleteMapping("/user")
    fun userDel() {
        service.userDel()

    }

    @GetMapping("/posttag")
    fun findPostTag() {
        service.get()
    }

}