package com.example.mayak.service

import com.example.mayak.Repository.RegionRepository
import com.example.mayak.Repository.UserRepository
import com.example.mayak.entity.PostKeyword
import com.example.mayak.entity.QPostKeyword
import com.example.mayak.entity.QUser
import com.example.mayak.entity.User
import com.example.mayak.requests.LoginRequest
import com.example.mayak.requests.SignUpRequest
import com.example.mayak.utils.HttpHeadersParser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalArgumentException

@Service
class UserService(
        private val userRepository: UserRepository,
        private val regionRepository: RegionRepository,
        private val queryFactory: JPAQueryFactory
) {

    @Transactional
    fun login(requset: LoginRequest) {
        val user = userRepository.findByAccount(requset.account)
                ?: throw IllegalArgumentException("Id를 찾을 수 없음. id: ${requset.account}")
        if (requset.password != user.password) throw IllegalArgumentException("비밀번호가 일치 하지 않음. ${requset.password}")
    }

    @Transactional
    fun signUp(requset: SignUpRequest) {

        val user = User(
                region = requset.region,
                account = requset.account,
                password = requset.password,
                profileImgPath = requset.profileImagePath,
                nickname = requset.nickname
        )
        userRepository.save(user)
    }

    fun getKeywords(headers: HttpHeaders): List<String> {
        val account = HttpHeadersParser.getAccount(headers)
        val user = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne() ?: throw IllegalArgumentException("사용자를 찾을 수 없음. id : $account")

        return queryFactory.select(QPostKeyword.postKeyword.keyword)
                .from(QPostKeyword.postKeyword)
                .where(QPostKeyword.postKeyword.user.eq(user))
                .fetch().toList()

    }
}