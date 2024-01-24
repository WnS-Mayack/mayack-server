package com.example.mayak.private

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class Service(private val pPostRepository: PPostRepository, private val tagRepository: TagRepository, private val ppPPostTagRepository: PPostTagRepository, private val userRepository: PUserRepository) {
    fun create() {
        val javaTag = tagRepository.findById(1L).get() // java
        val jsTag = tagRepository.findById(2L).get() // js

        val dh = userRepository.findById(1L).get()// puser dh
        val post = PPost(title = "new title1", puser = dh)

        val javaPT = PostTag(ppost = post, tag = javaTag)
        val jsPT = PostTag(ppost = post, tag = jsTag)
        ppPPostTagRepository.saveAll(mutableListOf(javaPT, jsPT))

        // 삭제 cascade
//        ppPPostTagRepository.deleteAll(mutableListOf(javaPT, jsPT))
        pPostRepository.deleteById(1L)


//        pPostRepository.save(post)
    }

    @Transactional
    fun delete() {
        ppPPostTagRepository.deleteById(1L)

//        pPostRepository.deleteById(1L)
    }

    @Transactional
    fun userDel() {
        userRepository.deleteById(1L)
    }

    fun get(){
        ppPPostTagRepository.findById(1L)
    }
}