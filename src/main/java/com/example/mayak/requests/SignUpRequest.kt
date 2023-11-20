package com.example.mayak.requests

import com.example.mayak.entity.Region

data class SignUpRequest(
        var region: String,
        var account:String,
        var password:String,
        var profileImagePath:String?,
        var nickname:String,
)