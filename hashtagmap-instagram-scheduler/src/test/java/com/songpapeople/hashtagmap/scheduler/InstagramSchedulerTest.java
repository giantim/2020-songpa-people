package com.songpapeople.hashtagmap.scheduler;

import com.songpapeople.hashtagmap.MockDataFactory;
import com.songpapeople.hashtagmap.dto.CrawlingDto;
import com.songpapeople.hashtagmap.dto.PostDtos;
import com.songpapeople.hashtagmap.instagram.domain.model.InstagramPost;
import com.songpapeople.hashtagmap.instagram.domain.repository.InstagramPostRepository;
import com.songpapeople.hashtagmap.place.domain.model.Place;
import com.songpapeople.hashtagmap.place.domain.repository.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class InstagramSchedulerTest {
    private InstagramScheduler instagramScheduler;

    @Mock
    private InstagramScheduleService instagramScheduleService;

    @Autowired
    private InstagramPostRepository instagramPostRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @BeforeEach
    void setUp() {
        instagramScheduler = new InstagramScheduler(instagramPostRepository, placeRepository, instagramScheduleService);
    }

    @DisplayName("db에서 place를 가져와 instagramPosts를 만들어 저장하는 기능 테스트")
    @Test
    void update() {
        Place place1 = Place.builder()
                .placeName("스타벅스")
                .build();
        Place place2 = Place.builder()
                .placeName("이디야")
                .build();
        List<Place> places = Arrays.asList(place1, place2);
        placeRepository.saveAll(places);
        PostDtos postDtos = MockDataFactory.createPostDtos();

        List<CrawlingResult> crawlingResults = Arrays.asList(
                new CrawlingResult(CrawlingDto.of(place1.getPlaceName(), "100", postDtos), place1),
                new CrawlingResult(CrawlingDto.of(place2.getPlaceName(), "100", postDtos), place2)
        );

        when(instagramScheduleService.createCrawlingResult(anyList()))
                .thenReturn(crawlingResults);

        instagramScheduler.update();

        List<InstagramPost> instagramPosts = instagramPostRepository.findAll();
        assertThat(instagramPosts).hasSize(18);
    }
}
