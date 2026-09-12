package main;

public record DateInterval(Date start, Date end) {

    /*
     * Test cases without tests:
     *
     * new DateInterval(
     *     new Date(2026, 1, 1),
     *     new Date(2026, 1, 10)
     * );
     *
     * new DateInterval(
     *     new Date(2026, 3, 15),
     *     new Date(2026, 4, 20)
     * );
     *
     * new DateInterval(
     *     new Date(2026, 12, 1),
     *     new Date(2026, 12, 31)
     * );
     */

    /*
     * Purpose: Create a DateInterval with a start and end date.
     * The end date cannot come before the start date.
     *
     * Tests:
     * new DateInterval(
     *     new Date(2026, 1, 1),
     *     new Date(2026, 1, 10)
     * ) should work.
     *
     * new DateInterval(
     *     new Date(2026, 1, 10),
     *     new Date(2026, 1, 1)
     * ) should throw IllegalArgumentException.
     */
    public DateInterval {
        if (!Date.comesBefore(start, end)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }
    }

    /*
     * Purpose: Return the number of 24-hour periods from noon
     * on the start date to noon on the end date.
     *
     * Tests:
     *
     * dateIntervalDays(
     *     new DateInterval(
     *         new Date(2026, 1, 1),
     *         new Date(2026, 1, 1)
     *     )
     * ) -> 0
     *
     * dateIntervalDays(
     *     new DateInterval(
     *         new Date(2026, 1, 1),
     *         new Date(2026, 1, 2)
     *     )
     * ) -> 1
     *
     * dateIntervalDays(
     *     new DateInterval(
     *         new Date(2026, 1, 1),
     *         new Date(2026, 2, 1)
     *     )
     * ) -> 31
     */
    public static int dateIntervalDays(DateInterval interval) {
        int days = 0;

        Date current = interval.start();

        while (!current.equals(interval.end())) {
            current = Date.tomorrow(current);
            days++;
        }

        return days;
    }

    /*
     * Purpose: Return true if two date intervals overlap.
     *
     * An interval starts at noon on its start date and ends at
     * 11:59 AM on its end date.
     *
     * Tests:
     *
     * [Jan 1, Jan 10] and [Jan 5, Jan 15] -> true
     * [Jan 1, Jan 10] and [Jan 10, Jan 15] -> false
     * [Jan 1, Jan 10] and [Jan 11, Jan 15] -> false
     * [Jan 1, Jan 10] and [Dec 1, Dec 31] -> false
     */
    public static boolean dateOverlap(DateInterval a, DateInterval b) {
        // a overlaps b if:
        // a.start is before b.end AND
        // b.start is before a.end,
        // where "before" here must be strictly before.

        return strictlyBefore(a.start(), b.end())
                && strictlyBefore(b.start(), a.end());
    }

    /*
     * Helper method.
     *
     * Purpose: Return true if date1 is strictly before date2.
     */
    private static boolean strictlyBefore(Date date1, Date date2) {
        return Date.comesBefore(date1, date2)
                && !Date.comesBefore(date2, date1);
    }

    /*
     * Implicit type:
     *
     * maybe-date-interval = DateInterval OR null
     *
     * Java does not support non-null types, so this is only
     * represented through comments and the use of null.
     *
     * Purpose: Return the intersection of two date intervals.
     * Return null if the intersection is empty.
     *
     * Tests:
     *
     * [Jan 1, Jan 10] and [Jan 5, Jan 15]
     *     -> [Jan 5, Jan 10]
     *
     * [Jan 1, Jan 10] and [Jan 10, Jan 15]
     *     -> null
     *
     * [Jan 1, Jan 10] and [Jan 11, Jan 15]
     *     -> null
     */
    public static DateInterval dateIntervalIntersect(
            DateInterval a,
            DateInterval b
    ) {
        if (!dateOverlap(a, b)) {
            return null;
        }

        // The intersection starts at the later start date.
        Date newStart;

        if (Date.comesBefore(a.start(), b.start())) {
            newStart = b.start();
        } else {
            newStart = a.start();
        }

        // The intersection ends at the earlier end date.
        Date newEnd;

        if (Date.comesBefore(a.end(), b.end())) {
            newEnd = a.end();
        } else {
            newEnd = b.end();
        }

        return new DateInterval(newStart, newEnd);
    }


    /*
     * maybe-date-interval = DateInterval OR null
     *
     * Purpose: Return the intersection of two maybe-date-intervals.
     * If either interval is null, return null.
     *
     * Tests:
     *
     * null and [Jan 1, Jan 10]
     *     -> null
     *
     * [Jan 1, Jan 10] and null
     *     -> null
     *
     * [Jan 1, Jan 10] and [Jan 5, Jan 15]
     *     -> [Jan 5, Jan 10]
     *
     * [Jan 1, Jan 10] and [Jan 11, Jan 15]
     *     -> null
     */
    public static DateInterval maybeDateIntervalIntersect(
            DateInterval a,
            DateInterval b
    ) {
        if (a == null || b == null) {
            return null;
        }

        return dateIntervalIntersect(a, b);
    }

}
