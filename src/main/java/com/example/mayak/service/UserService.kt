package com.example.mayak.service

import com.example.mayak.Repository.RegionRepository
import com.example.mayak.Repository.UserRepository
import com.example.mayak.entity.User
import com.example.mayak.requests.LoginRequest
import com.example.mayak.requests.SignUpRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class UserService(
        private val userRepository: UserRepository,
        private val regionRepository: RegionRepository,

        ) {

    @Transactional
    fun login(requset: LoginRequest) {
        val user = userRepository.findByAccount(requset.account)
                ?: throw IllegalArgumentException("Id를 찾을 수 없음. id: ${requset.account}")
        if (requset.password != user.password) throw IllegalArgumentException("비밀번호가 일치 하지 않음. ${requset.password}")
    }

    @Transactional
    fun signUp(requset: SignUpRequest) {
        val region = regionRepository.findById(requset.region).orElseThrow()
                ?: throw IllegalArgumentException("지역을 찾을 수 없음. ${requset.region}")
        val user = User(
                region = region,
                account = requset.account,
                password = requset.password,
                profileImgPath = requset.profileImagePath,
                nickname = requset.nickname
        )
        userRepository.save(user)
    }
}