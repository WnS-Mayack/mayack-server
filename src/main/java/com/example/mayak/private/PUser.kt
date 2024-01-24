package com.example.mayak.private

import jakarta.persistence.*

@Entity
@Table(name = "puser")
class PUser(name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    var name: String = name

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "puser", cascade = arrayOf(CascadeType.REMOVE), orphanRemoval = true)
    var posts: MutableList<PPost> = mutableListOf()

    // cascade 옵션에서 persist -> 영속화 전이 새당
    // 그래서 Proxy ( persitenceBag ) 타입으로 Eager하게 list로 들어고오는데, 현재는 아직 빈객체인듯? 그럼 Lazy와의 차이점이 궁금하다.
    // -> 후에 delete, 등 쿼리 날린 후 afterQueryCascade 와 비슷한 함수 명이 있는 것으로 보아.
    // 쿼리 실행 전후 해당 엔티티 (또는 list) 의 cascade 옵션을 확인 후 영속성 전이를 시킨다.

    // 이때 remove, 였다면 영속 제거 상태를 전이하는데 Post Tag -> Post -> Usser 까지 전파된다..
    // 그 후, User에서 OneToMany Eager + Cascade ALl 로, 다시 Post를 영속상태에 Persist 한다.
    // Post는 PostTag 를 ManyToOne Lazy + All 로 상태 전이한다. 이때 다시 PostTag가 영속상태로 영속화 되므로

    //최종적으로 persistence context 에 기존처럼 모두 영속화 된 상태이므로 제거 ㅝ리 발생안함

    // 그럼 Post 에서 PostTag 영속 전이 상태를 remove로 설정해놓으면 제거가 될듯하다.
    // 아니, PostTag는 영속 제거 대상이지만, Post가 영속화 된 상태라 FK제약 조건 위배 발생할듯
    // 실행결과 post_tag만 제거됨. 그치 post_tag가 있는 상태로 post가 안될뿐.

    //그럼 추가로 Puser에서 PostTag는 all 영속 전이 만들고
    //puser 에서 post는 영속 제거 만들고
    // 그러면 post는 제거 대상인ㄷ네, postTag가 살아있어서 FK제약 조건 위배 발생 예상.
}