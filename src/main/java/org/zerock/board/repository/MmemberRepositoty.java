package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.board.entity.Mmember;

public interface MmemberRepositoty extends JpaRepository<Mmember, Long> {
}
