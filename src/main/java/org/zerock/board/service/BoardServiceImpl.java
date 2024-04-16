package org.zerock.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor // 필수 매개변수를 이용한 생성자 자동 생성
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository; // BoardRepository 주입
    private final ReplyRepository replyRepository; // ReplyRepository 주입

    @Override
    public Long register(BoardDTO dto){ // 게시글 등록 메서드

        log.info(dto);

        Board board = dtoToEntity(dto); // DTO를 Entity로 변환

        repository.save(board); // 게시글 저장

        return board.getBno(); // 저장된 게시글의 번호 반환

    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) { // 페이지별 게시글 목록 조회 메서드

        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1],(Long)en[2])); // Object 배열을 BoardDTO로 변환하는 함수 정의
        // 게시글 엔티티, 회원 엔티티 받아서 BoardDTO로 변환

     //   Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );

        return new PageResultDTO<>(result, fn); // 페이지 결과 DTO 반환
    }

    @Override
    public BoardDTO get(Long bno) {

        Object result = repository.getBoardByBno(bno);
        // 게시글 번호로 해당 게시글 정보 조회

        Object[] arr = (Object[])result;
        // 조회된 결과를 Object 배열로 형변환

        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
        // Object 배열을 이용하여 BoardDTO로 변환
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {

        // 댓글 부터 삭제
        replyRepository.deleteByBno(bno);

        repository.deleteById(bno);
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = repository.getOne(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
    }

}
