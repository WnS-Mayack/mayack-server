package com.example.mayak.service

import com.example.mayak.Repository.RegionRepository
import com.example.mayak.Repository.UserRepository
import com.example.mayak.dto.PostDto
import com.example.mayak.dto.PostKeywordDto
import com.example.mayak.dto.UserDto
import com.example.mayak.entity.*
import com.example.mayak.requests.LoginRequest
import com.example.mayak.requests.PostKeywordRequest
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
        private val queryFactory: JPAQueryFactory,
        private val postKeywordRepository: PostKeywordRepository
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

    fun getKeywords(headers: HttpHeaders): PostKeywordDto {
        val account = HttpHeadersParser.getAccount(headers)
        val user = queryFactory.selectFrom(QUser.user)
                .where(QUser.user.account.eq(account))
                .fetchOne() ?: throw IllegalArgumentException("사용자를 찾을 수 없음. id : $account")

        val postKeywords = queryFactory.select(QPostKeyword.postKeyword.keyword)
                .from(QPostKeyword.postKeyword)
                .where(QPostKeyword.postKeyword.user.eq(user))
                .fetch()

        return PostKeywordDto.from(postKeywords)

    }

    @Transactional
    fun createKeywords(request: PostKeywordRequest, headers: HttpHeaders) {
        val account = HttpHeadersParser.getAccount(headers)
        val user = queryFactory.selectFrom(QUser.user).where(QUser.user.account.eq(account)).fetchOne()
                ?: throw IllegalArgumentException("사용자가 존재 하지 않음. id : $account")
        val postKeywords = request.keywords.map {
            PostKeyword(
                    user = user,
                    keyword = it
            )
        }.toList()
        postKeywordRepository.saveAll(postKeywords)
    }

    @Transactional(readOnly = true)
    fun get(headers: HttpHeaders): UserDto {
        val account = HttpHeadersParser.getAccount(headers)
        val self = queryFactory.selectFrom(QUser.user).where(QUser.user.account.eq(account)).fetchOne()
                ?: throw IllegalArgumentException("사용자가 존재 하지 않음. id : $account")
        return UserDto.from(self)
    }

    fun getSellItems(headers: HttpHeaders): List<PostDto> {
        val account = HttpHeadersParser.getAccount(headers)
        val self = queryFactory.selectFrom(QUser.user).where(QUser.user.account.eq(account)).fetchOne()
                ?: throw IllegalArgumentException("사용자가 존재 하지 않음. id : $account")

        val sellOrders = queryFactory.selectFrom(QSellOrders.sellOrders)
                .where(QSellOrders.sellOrders.user.eq(self))
                .fetch()


        val sellPosts = sellOrders.map {
            it.post
        }.toMutableList()

        return sellPosts.map {
            val post = queryFactory.selectFrom(QPost.post)
                    .where(QPost.post.eq(it))
                    .fetchOne() ?: throw IllegalArgumentException("에러")

            PostDto.fromPostForMyPage(post)
        }.toList()


    }

    fun getBuyItems(headers: HttpHeaders): List<PostDto> {
        val account = HttpHeadersParser.getAccount(headers)
        val self = queryFactory.selectFrom(QUser.user).where(QUser.user.account.eq(account)).fetchOne()
                ?: throw IllegalArgumentException("사용자가 존재 하지 않음. id : $account")

        val buyOrders = queryFactory.selectFrom(QBuyOrders.buyOrders)
                .where(QBuyOrders.buyOrders.user.eq(self))
                .fetch()


        val buyPosts = buyOrders.map {
            it.post
        }.toMutableList()

        return buyPosts.map {
            val post = queryFactory.selectFrom(QPost.post)
                    .where(QPost.post.eq(it))
                    .fetchOne() ?: throw IllegalArgumentException("에러")

            PostDto.fromPostForMyPage(post)
        }.toList()
    }
}