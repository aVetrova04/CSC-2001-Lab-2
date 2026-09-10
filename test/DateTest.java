import main.DateRecord.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DateTest {

    @Test
    void TestNegativeDayThrows()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Date(0, 2, 2023);
                }
        );
    }

    @Test
    void TestNegativeYearThrows()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Date(1, 2, -2023);
                }
        );
    }


    @Test
    void TestTomorrowNextDaySuccess()
    {
        // Setup
        Date today = new Date(5, 9, 2032);

        // Run
        Date tomorrow = Date.tomorrow(today);

        // Assert
        assertNotNull(tomorrow);
        assertEquals(6, tomorrow.day());
        assertEquals(9, tomorrow.month());
        assertEquals(2032, tomorrow.year());
    }

    @Test
    void TestTomorrowNextDayOfLastMonth()
    {
        // Setup
        Date today = new Date(31, 12, 2026);

        // Run
        Date tomorrow = Date.tomorrow(today);

        // Assert
        assertNotNull(tomorrow);
        assertEquals(1, tomorrow.day());
        assertEquals(1, tomorrow.month());
        assertEquals(2027, tomorrow.year());
    }

    @Test
    void TestTomorrowNextDayOfNotLastMonth()
    {
        // Setup
        Date today = new Date(28, 2, 2026);

        // Run
        Date tomorrow = Date.tomorrow(today);

        // Assert
        assertNotNull(tomorrow);
        assertEquals(1, tomorrow.day());
        assertEquals(3, tomorrow.month());
        assertEquals(2026, tomorrow.year());
    }
}
