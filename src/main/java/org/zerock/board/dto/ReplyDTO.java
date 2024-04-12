package org.zerock.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {

    private Long rno;

    private String text;

    private String replyer;

    private Long bno; // 게시글 번호

    private LocalDateTime regDate, modDate;
}
