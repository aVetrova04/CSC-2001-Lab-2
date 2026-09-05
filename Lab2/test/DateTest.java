package test;

import main.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTest {

    @Test
    void TestNegativeDayThrows()
    {
        // Setup

        // Run

        // Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Date(0, 2, 2023);
                }
        );
    }
}
