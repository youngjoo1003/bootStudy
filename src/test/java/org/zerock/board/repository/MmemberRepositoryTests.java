package org.zerock.board.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.board.entity.Mmember;

import java.util.stream.IntStream;

@SpringBootTest
public class MmemberRepositoryTests {

    @Autowired
    private MmemberRepositoty mmemberRepositoty;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Mmember mmember = Mmember.builder()
                    .email("r"+i +"@zerock.org")
                    .pw("1111")
                    .nickname("reviewer"+i).build();
            mmemberRepositoty.save(mmember);
        });
    } // insertMembers

    @Commit
    @Transactional
    @Test
    public void testDeleteMember(){

        Long mid = 1L;

        Mmember mmember = Mmember.builder().mid(mid).build();

        reviewRepository.deleteByMmember(mmember);
        mmemberRepositoty.deleteById(mid);

    }
}
