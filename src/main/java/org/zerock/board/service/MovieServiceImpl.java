package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.MovieDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Movie;
import org.zerock.board.entity.MovieImage;
import org.zerock.board.repository.MovieImageRepository;
import org.zerock.board.repository.MovieRepository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    private final MovieImageRepository ImageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String , Object> entityMap = dtoToEntity(movieDTO); // DTO 객체를 Entity 객체로 변환
        Movie movie =(Movie) entityMap.get("movie"); // Movie 객체 추출
        List<MovieImage> movieImageList= (List<MovieImage>) entityMap.get("imgList");

        // Movie 정보 저장
        movieRepository.save(movie);

        // MovieImage 정보 저장
        movieImageList.forEach(movieImage -> {
            ImageRepository.save(movieImage);

        });
        return movie.getMno();
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        // 특정 영화 번호 정보 조회
        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            MovieImage  movieImage = (MovieImage)arr[1];
            movieImageList.add(movieImage);
        });

        // 리뷰의 평균 점수 및 리뷰 수
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        // 페이징 처리된 영화 목록 조회
        Page<Object[]> result = movieRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });


        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie)arr[0] ,
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3])
        );

        return new PageResultDTO<>(result, fn);
    }
}