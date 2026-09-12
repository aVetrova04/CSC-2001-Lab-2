package main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestRecords {

    static void main(String[] args) {

        // Three examples of Date records
        Date date1 = new Date(2026, 1, 1);
        Date date2 = new Date(2026, 7, 4);
        Date date3 = new Date(2026, 12, 31);

        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
    }

    // Test that an invalid month throws IllegalArgumentException
    @Test
    public void testInvalidMonth() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Date(2026, 13, 1)
        );
    }

    // Test that an invalid day throws IllegalArgumentException
    @Test
    public void testInvalidDay() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Date(2026, 4, 31)
        );
    }

    // Test daysInMonth
    @Test
    public void testDaysInMonth() {
        assertEquals(31, Date.daysInMonth(1));
        assertEquals(28, Date.daysInMonth(2));
        assertEquals(31, Date.daysInMonth(3));
        assertEquals(30, Date.daysInMonth(4));
        assertEquals(31, Date.daysInMonth(5));
        assertEquals(30, Date.daysInMonth(6));
        assertEquals(31, Date.daysInMonth(7));
        assertEquals(31, Date.daysInMonth(8));
        assertEquals(30, Date.daysInMonth(9));
        assertEquals(31, Date.daysInMonth(10));
        assertEquals(30, Date.daysInMonth(11));
        assertEquals(31, Date.daysInMonth(12));

        assertThrows(
                IllegalArgumentException.class,
                () -> Date.daysInMonth(0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> Date.daysInMonth(13)
        );
    }

    // Test tomorrow
    @Test
    public void testTomorrow() {

        assertEquals(
                new Date(2026, 1, 2),
                Date.tomorrow(new Date(2026, 1, 1))
        );

        // Last day of a 31-day month
        assertEquals(
                new Date(2026, 2, 1),
                Date.tomorrow(new Date(2026, 1, 31))
        );

        // Last day of a 30-day month
        assertEquals(
                new Date(2026, 5, 1),
                Date.tomorrow(new Date(2026, 4, 30))
        );

        // Last day of February
        assertEquals(
                new Date(2026, 3, 1),
                Date.tomorrow(new Date(2026, 2, 28))
        );

        // Last day of the year
        assertEquals(
                new Date(2027, 1, 1),
                Date.tomorrow(new Date(2026, 12, 31))
        );
    }

    // Test dayOfYear
    @Test
    public void testDayOfYear() {

        // January 1 is day 0
        assertEquals(
                0,
                Date.dayOfYear(new Date(2026, 1, 1))
        );

        // January 2 is day 1
        assertEquals(
                1,
                Date.dayOfYear(new Date(2026, 1, 2))
        );

        // February 1 is day 31
        assertEquals(
                31,
                Date.dayOfYear(new Date(2026, 2, 1))
        );

        // March 1 is day 59
        assertEquals(
                59,
                Date.dayOfYear(new Date(2026, 3, 1))
        );

        // December 31 is day 364
        assertEquals(
                364,
                Date.dayOfYear(new Date(2026, 12, 31))
        );
    }

    // Test comesBefore
    @Test
    public void testComesBefore() {

        // Earlier date
        assertTrue(
                Date.comesBefore(
                        new Date(2026, 1, 1),
                        new Date(2026, 1, 2)
                )
        );

        // Same date
        assertTrue(
                Date.comesBefore(
                        new Date(2026, 1, 1),
                        new Date(2026, 1, 1)
                )
        );

        // Earlier month
        assertTrue(
                Date.comesBefore(
                        new Date(2026, 1, 15),
                        new Date(2026, 2, 1)
                )
        );

        // Earlier year
        assertTrue(
                Date.comesBefore(
                        new Date(2025, 12, 31),
                        new Date(2026, 1, 1)
                )
        );

        // Later date
        assertFalse(
                Date.comesBefore(
                        new Date(2026, 1, 2),
                        new Date(2026, 1, 1)
                )
        );

        // Later month
        assertFalse(
                Date.comesBefore(
                        new Date(2026, 3, 1),
                        new Date(2026, 2, 1)
                )
        );

        // Later year
        assertFalse(
                Date.comesBefore(
                        new Date(2027, 1, 1),
                        new Date(2026, 12, 31)
                )
        );
    }

    // Test DateInterval constructor
    @Test
    public void testDateIntervalConstructor() {

        DateInterval interval = new DateInterval(
                new Date(2026, 1, 1),
                new Date(2026, 1, 10)
        );

        assertEquals(new Date(2026, 1, 1), interval.start());
        assertEquals(new Date(2026, 1, 10), interval.end());

        assertThrows(
                IllegalArgumentException.class,
                () -> new DateInterval(
                        new Date(2026, 1, 10),
                        new Date(2026, 1, 1)
                )
        );
    }

    // Test dateIntervalDays
    @Test
    public void testDateIntervalDays() {

        assertEquals(
                1,
                DateInterval.dateIntervalDays(
                        new DateInterval(
                                new Date(2026, 1, 1),
                                new Date(2026, 1, 2)
                        )
                )
        );

        assertEquals(
                31,
                DateInterval.dateIntervalDays(
                        new DateInterval(
                                new Date(2026, 1, 1),
                                new Date(2026, 2, 1)
                        )
                )
        );

        assertEquals(
                1,
                DateInterval.dateIntervalDays(
                        new DateInterval(
                                new Date(2026, 12, 31),
                                new Date(2027, 1, 1)
                        )
                )
        );
    }

    // Test dateOverlap
    @Test
    public void testDateOverlap() {

        DateInterval a = new DateInterval(
                new Date(2026, 1, 1),
                new Date(2026, 1, 10)
        );

        DateInterval b = new DateInterval(
                new Date(2026, 1, 5),
                new Date(2026, 1, 15)
        );

        // Overlapping intervals
        assertTrue(DateInterval.dateOverlap(a, b));
        assertTrue(DateInterval.dateOverlap(b, a));

        // Touching at the endpoint does NOT overlap
        DateInterval c = new DateInterval(
                new Date(2026, 1, 10),
                new Date(2026, 1, 20)
        );

        assertFalse(DateInterval.dateOverlap(a, c));

        // Completely separate intervals
        DateInterval d = new DateInterval(
                new Date(2026, 2, 1),
                new Date(2026, 2, 10)
        );

        assertFalse(DateInterval.dateOverlap(a, d));
    }

    // Test dateIntervalIntersect
    @Test
    public void testDateIntervalIntersect() {

        DateInterval a = new DateInterval(
                new Date(2026, 1, 1),
                new Date(2026, 1, 10)
        );

        DateInterval b = new DateInterval(
                new Date(2026, 1, 5),
                new Date(2026, 1, 15)
        );

        // Overlapping intervals
        assertEquals(
                new DateInterval(
                        new Date(2026, 1, 5),
                        new Date(2026, 1, 10)
                ),
                DateInterval.dateIntervalIntersect(a, b)
        );

        // No overlap
        DateInterval c = new DateInterval(
                new Date(2026, 1, 20),
                new Date(2026, 1, 30)
        );

        assertNull(
                DateInterval.dateIntervalIntersect(a, c)
        );

        // Touching endpoints is an empty intersection
        DateInterval d = new DateInterval(
                new Date(2026, 1, 10),
                new Date(2026, 1, 20)
        );

        assertNull(
                DateInterval.dateIntervalIntersect(a, d)
        );
    }

    // Test maybeDateIntervalIntersect
    @Test
    public void testMaybeDateIntervalIntersect() {

        DateInterval a = new DateInterval(
                new Date(2026, 1, 1),
                new Date(2026, 1, 10)
        );

        DateInterval b = new DateInterval(
                new Date(2026, 1, 5),
                new Date(2026, 1, 15)
        );

        // Two non-null overlapping intervals
        assertEquals(
                new DateInterval(
                        new Date(2026, 1, 5),
                        new Date(2026, 1, 10)
                ),
                DateInterval.maybeDateIntervalIntersect(a, b)
        );

        // First interval is null
        assertNull(
                DateInterval.maybeDateIntervalIntersect(null, a)
        );

        // Second interval is null
        assertNull(
                DateInterval.maybeDateIntervalIntersect(a, null)
        );

        // Both intervals are null
        assertNull(
                DateInterval.maybeDateIntervalIntersect(null, null)
        );

        // Two non-null intervals that do not overlap
        DateInterval c = new DateInterval(
                new Date(2026, 2, 1),
                new Date(2026, 2, 10)
        );

        assertNull(
                DateInterval.maybeDateIntervalIntersect(a, c)
        );
    }

    // Test listLen
    @Test
    public void testListLen() {

        assertEquals(
                0,
                DateList.listLen(null)
        );

        DateList one = new DateList(
                new Date(2026, 1, 1),
                null
        );

        assertEquals(
                1,
                DateList.listLen(one)
        );

        DateList three = new DateList(
                new Date(2026, 1, 1),
                new DateList(
                        new Date(2026, 1, 2),
                        new DateList(
                                new Date(2026, 1, 3),
                                null
                        )
                )
        );

        assertEquals(
                3,
                DateList.listLen(three)
        );
    }


    // Test minDate
    @Test
    public void testMinDate() {

        assertNull(
                DateList.minDate(null)
        );

        DateList one = new DateList(
                new Date(2026, 1, 5),
                null
        );

        assertEquals(
                new Date(2026, 1, 5),
                DateList.minDate(one)
        );

        DateList dates = new DateList(
                new Date(2026, 1, 5),
                new DateList(
                        new Date(2026, 1, 1),
                        new DateList(
                                new Date(2026, 1, 10),
                                null
                        )
                )
        );

        assertEquals(
                new Date(2026, 1, 1),
                DateList.minDate(dates)
        );
    }


    // Test maxDate
    @Test
    public void testMaxDate() {

        assertNull(
                DateList.maxDate(null)
        );

        DateList one = new DateList(
                new Date(2026, 1, 5),
                null
        );

        assertEquals(
                new Date(2026, 1, 5),
                DateList.maxDate(one)
        );

        DateList dates = new DateList(
                new Date(2026, 1, 5),
                new DateList(
                        new Date(2026, 1, 1),
                        new DateList(
                                new Date(2026, 1, 10),
                                null
                        )
                )
        );

        assertEquals(
                new Date(2026, 1, 10),
                DateList.maxDate(dates)
        );
    }


    // Test dateCover
    @Test
    public void testDateCover() {

        assertNull(
                DateList.dateCover(null)
        );

        DateList one = new DateList(
                new Date(2026, 1, 5),
                null
        );

        assertEquals(
                new DateInterval(
                        new Date(2026, 1, 5),
                        new Date(2026, 1, 5)
                ),
                DateList.dateCover(one)
        );

        DateList dates = new DateList(
                new Date(2026, 1, 5),
                new DateList(
                        new Date(2026, 1, 1),
                        new DateList(
                                new Date(2026, 1, 10),
                                null
                        )
                )
        );

        assertEquals(
                new DateInterval(
                        new Date(2026, 1, 1),
                        new Date(2026, 1, 10)
                ),
                DateList.dateCover(dates)
        );
    }


    // Test allTomorrows
    @Test
    public void testAllTomorrows() {

        assertNull(
                DateList.allTomorrows(null)
        );

        DateList dates = new DateList(
                new Date(2026, 1, 1),
                new DateList(
                        new Date(2026, 1, 5),
                        new DateList(
                                new Date(2026, 1, 10),
                                null
                        )
                )
        );

        DateList expected = new DateList(
                new Date(2026, 1, 2),
                new DateList(
                        new Date(2026, 1, 6),
                        new DateList(
                                new Date(2026, 1, 11),
                                null
                        )
                )
        );

        assertEquals(
                expected,
                DateList.allTomorrows(dates)
        );
    }


    // Test addToEnd
    @Test
    public void testAddToEnd() {

        assertEquals(
                new DateList(
                        new Date(2026, 1, 1),
                        null
                ),
                DateList.addToEnd(
                        null,
                        new Date(2026, 1, 1)
                )
        );

        DateList dates = new DateList(
                new Date(2026, 1, 1),
                new DateList(
                        new Date(2026, 1, 2),
                        null
                )
        );

        DateList expected = new DateList(
                new Date(2026, 1, 1),
                new DateList(
                        new Date(2026, 1, 2),
                        new DateList(
                                new Date(2026, 1, 3),
                                null
                        )
                )
        );

        assertEquals(
                expected,
                DateList.addToEnd(
                        dates,
                        new Date(2026, 1, 3)
                )
        );
    }


    // Test append
    @Test
    public void testAppend() {

        DateList list1 = new DateList(
                new Date(2026, 1, 1),
                new DateList(
                        new Date(2026, 1, 2),
                        null
                )
        );

        DateList list2 = new DateList(
                new Date(2026, 1, 3),
                new DateList(
                        new Date(2026, 1, 4),
                        null
                )
        );

        DateList expected = new DateList(
                new Date(2026, 1, 1),
                new DateList(
                        new Date(2026, 1, 2),
                        new DateList(
                                new Date(2026, 1, 3),
                                new DateList(
                                        new Date(2026, 1, 4),
                                        null
                                )
                        )
                )
        );

        assertEquals(
                expected,
                DateList.append(list1, list2)
        );

        assertEquals(
                list2,
                DateList.append(null, list2)
        );

        assertEquals(
                list1,
                DateList.append(list1, null)
        );

        assertNull(
                DateList.append(null, null)
        );
    }
}
