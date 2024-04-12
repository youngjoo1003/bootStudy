package org.zerock.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor // 모든 필드 생성자
@NoArgsConstructor // 기본 생성자
@Getter
@ToString
public class Member extends BaseEntity {

    @Id // pk
    private String email;

    private String password;

    private String name;
}
