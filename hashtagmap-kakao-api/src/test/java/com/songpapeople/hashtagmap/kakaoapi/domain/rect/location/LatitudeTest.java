package com.songpapeople.hashtagmap.kakaoapi.domain.rect.location;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LatitudeTest {
    @DisplayName("정상 범위로 생성했을 때")
    @ParameterizedTest
    @ValueSource(doubles = {33, 35, 43})
    public void latitudeConstructTest(double value) {
        assertThat(new Latitude(value)).isInstanceOf(Coordinate.class);
    }

    @DisplayName("정상 범위가 아닐 때")
    @ParameterizedTest
    @ValueSource(doubles = {32.7, 43.1})
    public void invalidLatitudeConstructTest(double value) {
        assertThatThrownBy(() -> new Latitude(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("위도 범위가 아닙니다.");
    }
}