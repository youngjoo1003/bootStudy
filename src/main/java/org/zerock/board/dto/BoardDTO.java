package org.zerock.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    
    private Long bno; // 번호
    
    private String title; // 제목
    
    private String content; // 내용
    
    private String writerEmail; // 작성자 이메일(id)
    
    private String writerName; // 작성자 이름
    
    private LocalDateTime regDate; // 등록일
    
    private LocalDateTime modDate; // 수정일
    
    private int replyCount; // 댓글 수
}
