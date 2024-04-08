package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_memo") // db 테이블 명을 지정
@ToString // 객체가 아닌 문자로
@Getter
@Builder // 메서드.필드(값).필드(값).builder; (빌더 패턴)
// @AllArgsConstructor @NoArgsConstructor 두 가지 필수

@AllArgsConstructor // @AllArgsConstructor ( new 클래스(모든 필드 값 피라미터로 만듦)
@NoArgsConstructor // @NoArgsConstructor (new 클래스(); )
public class Memo {
    // 엔티티는 데이터베이스에 테이블과 필드를 생성시켜 관리하는 객체
    // 엔티티를 이용해서 jpa를 활성화 하려면 application.properties 에 필수 항목 추가

    @Id // 기본키를 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 스트래터지(방법,계략)
    // auto : JPA 구현체가 생성 방식을 결정
    // IDENTITY : MariaDB용 (auto increment)
    // SEQUENCE : Oracle용 (@SequenceGenerator와 같이 사용)
    // TABLE : 키생성 전용 테이블을 생성해 키 생성(@TableGenerator와 같이 사용)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

}
