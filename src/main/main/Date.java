package main;

public record Date(int year, int month, int day) {

    /*
     * Constructor:
     * Purpose: Create a Date while ensuring the month and day are valid.
     * Tests:
     * - new Date(2026, 1, 1) should work.
     * - new Date(2026, 13, 1) should throw IllegalArgumentException.
     * - new Date(2026, 1, 0) should throw IllegalArgumentException.
     * - new Date(2026, 4, 31) should throw IllegalArgumentException.
     */
    public Date {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        if (day < 1 || day > daysInMonth(month)) {
            throw new IllegalArgumentException(
                    "Day must be valid for the given month"
            );
        }
    }

    /*
     * Purpose: Return the number of days in the given month.
     *
     * Tests:
     * daysInMonth(1)  -> 31
     * daysInMonth(2)  -> 28
     * daysInMonth(4)  -> 30
     * daysInMonth(12) -> 31
     * daysInMonth(0)  -> IllegalArgumentException
     * daysInMonth(13) -> IllegalArgumentException
     */
    public static int daysInMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        if (month == 2) {
            return 28;
        }

        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }

        return 31;
    }

    /*
     * Purpose: Return the Date representing the day immediately after date.
     *
     * Tests:
     * tomorrow(new Date(2026, 1, 1))
     *     -> new Date(2026, 1, 2)
     *
     * tomorrow(new Date(2026, 1, 31))
     *     -> new Date(2026, 2, 1)
     *
     * tomorrow(new Date(2026, 12, 31))
     *     -> new Date(2027, 1, 1)
     */
    public static Date tomorrow(Date date) {
        if (date.day() < daysInMonth(date.month())) {
            return new Date(date.year(), date.month(), date.day() + 1);
        }

        if (date.month() < 12) {
            return new Date(date.year(), date.month() + 1, 1);
        }

        return new Date(date.year() + 1, 1, 1);
    }

    /*
     * Purpose: Return the number of days elapsed since January 1.
     * January 1 is day 0.
     *
     * Tests:
     * dayOfYear(new Date(2026, 1, 1))  -> 0
     * dayOfYear(new Date(2026, 1, 2))  -> 1
     * dayOfYear(new Date(2026, 2, 1))  -> 31
     * dayOfYear(new Date(2026, 3, 1))  -> 59
     * dayOfYear(new Date(2026, 12, 31)) -> 364
     */
    public static int dayOfYear(Date date) {
        int total = 0;

        for (int month = 1; month < date.month(); month++) {
            total += daysInMonth(month);
        }

        total += date.day() - 1;

        return total;
    }

    /*
     * Purpose: Return true if date1 comes before date2 or is the same date.
     *
     * Tests:
     * comesBefore(new Date(2026, 1, 1), new Date(2026, 1, 2)) -> true
     * comesBefore(new Date(2026, 1, 2), new Date(2026, 1, 1)) -> false
     * comesBefore(new Date(2026, 1, 1), new Date(2026, 1, 1)) -> true
     * comesBefore(new Date(2025, 12, 31), new Date(2026, 1, 1)) -> true
     */
    public static boolean comesBefore(Date date1, Date date2) {
        if (date1.year() < date2.year()) {
            return true;
        }

        if (date1.year() > date2.year()) {
            return false;
        }

        if (date1.month() < date2.month()) {
            return true;
        }

        if (date1.month() > date2.month()) {
            return false;
        }

        return date1.day() <= date2.day();
    }
}
