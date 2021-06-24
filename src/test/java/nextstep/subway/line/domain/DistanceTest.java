package nextstep.subway.line.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class DistanceTest {

    @Test
    @DisplayName("생성 테스트")
    void create() {
        // given & when & then
        assertThat(Distance.of(5)).isEqualTo(Distance.of(5));
        assertThat(Distance.of(5).toNumber()).isEqualTo(5);
        assertThat(Distance.of(10)).isEqualTo(Distance.of(10));
        assertThat(Distance.of(10).toNumber()).isEqualTo(10);
    }

    @Test
    @DisplayName("유효하지 않은 거리(0보다 작은) 생성 하는 경우 실패 테스트")
    void validate() {
        // given & when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Distance.of(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Distance.of(0));
    }

    @ParameterizedTest(name = "현재 거리보다 짧은 거리인 경우 수정 가능 테스트")
    @CsvSource(value = {"1:9", "3:7", "5:5", "9:1"}, delimiter = ':')
    void updateDiffDistance(int input, int expected) {
        // given
        Distance distance = Distance.of(10);
        // when
        distance.subtractDistance(input);
        // then
        assertThat(distance.toNumber()).isEqualTo(expected);
    }

    @ParameterizedTest(name = "현재 거리보다 같거나 긴 경우 수정 불가능 테스트")
    @ValueSource(strings = {"5", "10", "15"})
    void subtractDistance(int input) {
        // given
        Distance distance = Distance.of(5);
        // when & then
        Assertions.assertThrows(RuntimeException.class, () -> distance.subtractDistance(input));
    }
}
