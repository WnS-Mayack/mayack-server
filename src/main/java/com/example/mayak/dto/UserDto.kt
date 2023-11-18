package com.example.mayak.dto

import com.example.mayak.entity.User
import java.io.Serializable

/**
 * DTO for {@link com.example.mayak.entity.User}
 */
data class UserDto(
        var id: Long,
        var region: String,
        var account: String,
        var profileImgPath: String?,
        var nickname: String
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                    id = user.id,
                    region = user.region,
                    account = user.account,
                    profileImgPath = user.profileImgPath,
                    nickname = user.nickname
            )
        }
    }
}