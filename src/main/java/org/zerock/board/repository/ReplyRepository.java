package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying //  UPDATE 또는 DELETE 쿼리를 실행함
    @Query("DELETE FROM Reply r WHERE r.board.bno =:bno ")
    void deleteByBno(Long bno);
}
