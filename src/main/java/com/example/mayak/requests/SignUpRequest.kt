package com.example.mayak.requests

import com.example.mayak.entity.Region

data class SignUpRequest(
        var region: Long,
        var account:String,
        var password:String,
        var profileImagePath:String?,
        var nickname:String,
)