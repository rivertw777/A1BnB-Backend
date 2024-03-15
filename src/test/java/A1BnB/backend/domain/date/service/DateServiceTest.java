package A1BnB.backend.domain.date.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class DateServiceTest {

    @Autowired
    private DateService dateService;
    @Test
    void getDates() {
    }

    @Test
    void getLocalDateTimeDates() {
        LocalDateTime checkInDate = LocalDateTime.parse("2024-03-15T00:00");
        LocalDateTime checkOutDate = LocalDateTime.parse("2024-03-30T00:00");
        List<LocalDateTime> localDateTimes = dateService.getLocalDateTimeDates(checkInDate, checkOutDate);
        assertThat(localDateTimes.size()).isEqualTo(16);
    }

    @Test
    void deleteFromAvailableDates() {
    }

    @Test
    void revertToAvailableDates() {
    }
}