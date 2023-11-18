package com.example.mayak.utils

import org.springframework.http.HttpHeaders

class HttpHeadersParser {
    companion object {
        fun getAccount(headers: HttpHeaders): String {
            val header = headers.get("account") ?: throw IllegalArgumentException("헤더가 존재 하지 않음. $headers")
            return header.get(0)
        }
    }
}