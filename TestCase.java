package Time_Calculation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestCase {

    @Test
    public void t_ParseTimeString() {
        Time_calc timeCalc = new Time_calc();
        LocalTime etime = LocalTime.of(9, 0);
        LocalTime atime = timeCalc.parseTimeString("0900");
        assertEquals(etime, atime);
    }

    @Test
    public void t_ConvertToInstant() {
        Time_calc timeCalc = new Time_calc();
        Instant eInstant = Instant.parse("2024-05-06T03:30:00Z");
        Instant aInstant = timeCalc.convertToInstant(LocalTime.of(9, 0),LocalDate.of(2024,05, 06));
        assertEquals(eInstant, aInstant);
    }

    @Test
    public void t_Duration_Between_Days() {
        Time_calc timeCalc = new Time_calc();
        Instant end = Instant.parse("2024-05-06T17:30:00Z");
        Instant start = Instant.parse("2024-05-06T09:00:00Z");
        Duration eDuration = Duration.ofHours(15).plusMinutes(30);
        Duration aDuration = timeCalc.Duration_Between_Days(end, start);
        assertEquals(eDuration, aDuration);
    }
    
    @Test
    public void t_parseBreakTime() {
        Time_calc timeCalc = new Time_calc();
        String BreakTime = "B40";
        Duration expected = Duration.ofMinutes(40);
        assertEquals(expected, timeCalc.parseBreakTime(BreakTime));
}
}
