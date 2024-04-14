package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Mmember;
import org.zerock.board.entity.Movie;
import org.zerock.board.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews(){

        IntStream.rangeClosed(1,200).forEach(i -> {

            // 영화 번호
            Long mno = (long)(Math.random()*100) + 1;

            // 리뷰어 번호
            Long mid = ((long)(Math.random()*100) + 1);
            Mmember mmember = Mmember.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .mmember(mmember)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5)+1)
                    .text("이 영화에 대한 느낌..."+i)
                    .build();
            reviewRepository.save(movieReview);
        });
    } // insertMovieReviews

    @Test
    public void testGetMovieReviews(){

        Movie movie = Movie.builder().mno(92L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {

            System.out.println(movieReview.getReviewnum());
            System.out.println("\t"+movieReview.getGrade());
            System.out.println("\t"+movieReview.getText());
            System.out.println("\t"+movieReview.getMmember().getEmail());
            System.out.println("----------------------");
        });
    }
}
