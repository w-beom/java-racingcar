package step3.study.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import step3.study.dto.RequestRacingDTO;
import step3.study.util.FixedNumberGenerator;
import step3.study.util.NumberGenerator;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DriversTest {
    private RequestRacingDTO requestRacingDTO;
    private Drivers drivers;

    @BeforeEach
    void setUp() {
        List<String> names = Arrays.asList("pobi", "crong", "honux");
        requestRacingDTO = new RequestRacingDTO(names, new Round("3"));
        drivers = new Drivers(Drivers.of(requestRacingDTO));
    }

    @Test
    @DisplayName("우승자가 한 명일 때 테스트")
    void winnerTest() {
        drivers.getDriverList().get(0).moveCar(4);
        assertThat(drivers.getWinnerNames().size()).isEqualTo(1);
        assertThat(drivers.getWinnerNames().contains("pobi")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"pobi","crong","honux"})
    @DisplayName("모두 우승자일 때 테스트")
    void allWinnerTest(String winnerName){
        NumberGenerator fixedNumberGenerator = new FixedNumberGenerator(5);
        drivers.moveCars(fixedNumberGenerator);
        assertThat(drivers.getWinnerNames().contains(winnerName)).isTrue();
    }
}
