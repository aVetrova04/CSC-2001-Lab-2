import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateAssignment {

    public record Date(int day, int month, int year) {

        public Date {
           // Check if any of the days, months or years passed are illegal

            if (year < 0) {
                throw new IllegalArgumentException("Year not valid.");
            }

            if (month <= 0 || month > 12) {
                throw new IllegalArgumentException("Month not valid.");
            }

            if (day <= 0) {
                throw new IllegalArgumentException("Day not valid.");
            }

            if (month == 2 && day > 28) {
                throw new IllegalArgumentException("Day out of range.");
            }

            if ((month == 4 || month == 6 || month == 9 || month == 11)
                    && day > 30) {
                throw new IllegalArgumentException("Day out of range.");
            }

            if ((month == 1 || month == 3 || month == 5 || month == 7
                    || month == 8 || month == 10 || month == 12)
                    && day > 31) {
                throw new IllegalArgumentException("Day out of range.");
            }
        }
    }

     // PURPOSE: Return the number of days in the given month.

    public static int daysInMonth(int month) {

        return switch (month) {
            case 2 -> 28;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
    }

    // PURPOSE: Return the date representing the day after the given date.

    public static Date tomorrow(Date date) {
        if (date.day() < daysInMonth(date.month())) {
            return new Date(date.day() + 1, date.month(), date.year());
        }
        if (date.day() == daysInMonth(date.month())) {
            return new Date(1, date.month()+1, date.year);
        }
        if (date.day() == daysInMonth(date.month()) && date.month == 12) {
            return new Date(1, 1, date.year+1);
        }


    }


    public void main(String[] args) {
        Date time1 = new Date(1, 2, 2023);
        System.out.println(tomorrow(time1));

    }























    /*
     * PURPOSE:
     * Return the number of 24-hour periods between noon on
     * January 1 and noon on the given date.
     *
     * TEST CASES:
     * dayOfYear(new Date(1, 1, 2020)) -> 0
     * dayOfYear(new Date(2, 1, 2020)) -> 1
     * dayOfYear(new Date(1, 2, 2020)) -> 31
     * dayOfYear(new Date(1, 3, 2020)) -> 59
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
     * PURPOSE:
     * Return true if first comes before second, or if they are equal.
     *
     * TEST CASES:
     * comesBefore(1/1/2020, 2/1/2020) -> true
     * comesBefore(2/1/2020, 1/1/2020) -> false
     * comesBefore(1/1/2020, 1/1/2020) -> true
     */
    public static boolean comesBefore(Date first, Date second) {
        if (first.year() != second.year()) {
            return first.year() < second.year();
        }

        if (first.month() != second.month()) {
            return first.month() < second.month();
        }

        return first.day() <= second.day();
    }


    /*
     * JUnit TEST:
     *
     * assertThrows(IllegalArgumentException.class,
     *     () -> new Date(32, 1, 2020));
     *
     * assertThrows(IllegalArgumentException.class,
     *     () -> new Date(1, 13, 2020));
     *
     * assertThrows(IllegalArgumentException.class,
     *     () -> new Date(29, 2, 2020));
     */


    // Main method: three examples of Date records.
    public static void main(String[] args) {
        Date date1 = new Date(30, 11, 1987);
        Date date2 = new Date(12, 3, 1770);
        Date date3 = new Date(9, 1, 9);

        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
    }


    // ============================================================
    // 2.2 Date Ranges
    // ============================================================

    public record DateInterval(Date start, Date end) {

        public DateInterval {
            if (!comesBefore(start, end)) {
                throw new IllegalArgumentException(
                        "End date cannot be before start date."
                );
            }
        }
    }


    /*
     * TEST CASES WITHOUT TESTS:
     *
     * DateInterval interval1 =
     *     new DateInterval(
     *         new Date(1, 1, 2020),
     *         new Date(10, 1, 2020));
     *
     * DateInterval interval2 =
     *     new DateInterval(
     *         new Date(15, 3, 2020),
     *         new Date(20, 3, 2020));
     *
     * DateInterval interval3 =
     *     new DateInterval(
     *         new Date(1, 6, 2020),
     *         new Date(30, 6, 2020));
     */


    /*
     * PURPOSE:
     * Return the number of 24-hour periods from noon on the
     * start date to noon on the end date.
     *
     * TEST CASES:
     * dateIntervalDays(1/1 -> 1/1) -> 0
     * dateIntervalDays(1/1 -> 1/2) -> 1
     * dateIntervalDays(1/1 -> 10/1) -> 9
     */
    public static int dateIntervalDays(DateInterval interval) {
        int days = dayOfYear(interval.end()) - dayOfYear(interval.start());

        if (interval.start().year() != interval.end().year()) {
            for (int year = interval.start().year();
                 year < interval.end().year();
                 year++) {
                days += 365;
            }
        }

        return days;
    }


    /*
     * PURPOSE:
     * Return true when two date intervals overlap.
     *
     * An interval begins at noon on its start date and ends just
     * before noon on its end date, so two intervals that only meet
     * at the same date do NOT overlap.
     *
     * TEST CASES:
     *
     * [Jan 1, Jan 10] and [Jan 5, Jan 15] -> true
     * [Jan 1, Jan 10] and [Jan 10, Jan 15] -> false
     * [Jan 1, Jan 10] and [Jan 11, Jan 15] -> false
     */
    public static boolean dateOverlap(
            DateInterval first,
            DateInterval second) {

        return comesBefore(first.start(), second.end())
                && comesBefore(second.start(), first.end())
                && !first.end().equals(second.start())
                && !second.end().equals(first.start());
    }


    // ============================================================
    // 2.3 Maybe Date Ranges
    // ============================================================

    /*
     * MAYBE-DATE-INTERVAL:
     *
     * A maybe-date-interval is either:
     *
     *     DateInterval
     *
     * or
     *
     *     null
     *
     * null represents an empty interval.
     */


    /*
     * PURPOSE:
     * Return the intersection of two date intervals.
     * Return null if they do not overlap.
     *
     * TEST CASES:
     *
     * [Jan 1, Jan 10] ∩ [Jan 5, Jan 15]
     *     -> [Jan 5, Jan 10]
     *
     * [Jan 1, Jan 10] ∩ [Jan 10, Jan 15]
     *     -> null
     *
     * [Jan 1, Jan 5] ∩ [Jan 10, Jan 15]
     *     -> null
     */
    public static DateInterval dateIntervalIntersect(
            DateInterval first,
            DateInterval second) {

        Date latestStart;

        if (comesBefore(first.start(), second.start())) {
            latestStart = second.start();
        } else {
            latestStart = first.start();
        }

        Date earliestEnd;

        if (comesBefore(first.end(), second.end())) {
            earliestEnd = first.end();
        } else {
            earliestEnd = second.end();
        }

        // The intervals must contain some actual time in common.
        if (comesBefore(latestStart, earliestEnd)
                && !latestStart.equals(earliestEnd)) {
            return new DateInterval(latestStart, earliestEnd);
        }

        return null;
    }


    /*
     * PURPOSE:
     * Return the intersection of two maybe-date-intervals.
     * null represents an empty interval.
     *
     * TEST CASES:
     *
     * null ∩ interval -> null
     * interval ∩ null -> null
     * interval1 ∩ interval2 -> their intersection
     */
    public static DateInterval maybeDateIntervalIntersect(
            DateInterval first,
            DateInterval second) {

        if (first == null || second == null) {
            return null;
        }

        return dateIntervalIntersect(first, second);
    }


    // ============================================================
    // 2.4 Date Lists
    // ============================================================

    /*
     * A DateList is either:
     *
     *     null
     *
     * or
     *
     *     new DateList(first, rest)
     *
     * null represents the empty list.
     */
    public record DateList(Date first, DateList rest) {
    }


    /*
     * PURPOSE:
     * Return the length of a DateList.
     *
     * TEST CASES:
     * listLen(null) -> 0
     * listLen([date]) -> 1
     * listLen([date1, date2, date3]) -> 3
     */
    public static int listLen(DateList dates) {
        return switch (dates) {
            case null -> 0;
            case DateList(Date first, DateList rest) ->
                    1 + listLen(rest);
        };
    }


    /*
     * PURPOSE:
     * Return the earliest date in a list.
     *
     * TEST CASES:
     * minDate([Jan 5]) -> Jan 5
     * minDate([Jan 5, Jan 2, Jan 10]) -> Jan 2
     * minDate(null) -> IllegalArgumentException
     */
    public static Date minDate(DateList dates) {
        return switch (dates) {
            case null ->
                    throw new IllegalArgumentException(
                            "Cannot find minimum of empty list.");

            case DateList(Date first, DateList rest) -> {
                if (rest == null) {
                    yield first;
                }

                Date restMin = minDate(rest);

                if (comesBefore(first, restMin)) {
                    yield first;
                } else {
                    yield restMin;
                }
            }
        };
    }


    /*
     * PURPOSE:
     * Return the latest date in a list.
     *
     * TEST CASES:
     * maxDate([Jan 5]) -> Jan 5
     * maxDate([Jan 5, Jan 2, Jan 10]) -> Jan 10
     * maxDate(null) -> IllegalArgumentException
     */
    public static Date maxDate(DateList dates) {
        return switch (dates) {
            case null ->
                    throw new IllegalArgumentException(
                            "Cannot find maximum of empty list.");

            case DateList(Date first, DateList rest) -> {
                if (rest == null) {
                    yield first;
                }

                Date restMax = maxDate(rest);

                if (comesBefore(restMax, first)) {
                    yield first;
                } else {
                    yield restMax;
                }
            }
        };
    }


    /*
     * PURPOSE:
     * Return the shortest DateInterval containing every date
     * in the list.
     *
     * TEST CASES:
     *
     * dateCover([Jan 5, Jan 2, Jan 10])
     *     -> [Jan 2, Jan 10]
     *
     * dateCover([Mar 5])
     *     -> [Mar 5, Mar 5]
     *
     * dateCover(null)
     *     -> IllegalArgumentException
     */
    public static DateInterval dateCover(DateList dates) {
        return new DateInterval(minDate(dates), maxDate(dates));
    }


    /*
     * PURPOSE:
     * Return a new list in which every date is replaced by
     * tomorrow's date.
     *
     * TEST CASES:
     *
     * allTomorrows([Jan 1, Jan 2])
     *     -> [Jan 2, Jan 3]
     *
     * allTomorrows(null)
     *     -> null
     */
    public static DateList allTomorrows(DateList dates) {
        return switch (dates) {
            case null -> null;

            case DateList(Date first, DateList rest) ->
                    new DateList(tomorrow(first), allTomorrows(rest));
        };
    }


    /*
     * PURPOSE:
     * Return a new list with date added to the end.
     *
     * TEST CASES:
     *
     * addToEnd(null, Jan 1)
     *     -> [Jan 1]
     *
     * addToEnd([Jan 1, Jan 2], Jan 3)
     *     -> [Jan 1, Jan 2, Jan 3]
     */
    public static DateList addToEnd(DateList dates, Date date) {
        return switch (dates) {
            case null -> new DateList(date, null);

            case DateList(Date first, DateList rest) ->
                    new DateList(first, addToEnd(rest, date));
        };
    }


    /*
     * PURPOSE:
     * Return a new list containing all dates from first followed
     * by all dates from second.
     *
     * TEST CASES:
     *
     * append([Jan 1, Jan 2], [Jan 3, Jan 4])
     *     -> [Jan 1, Jan 2, Jan 3, Jan 4]
     *
     * append(null, [Jan 1])
     *     -> [Jan 1]
     *
     * append([Jan 1], null)
     *     -> [Jan 1]
     */
    public static DateList append(
            DateList first,
            DateList second) {

        return switch (first) {
            case null -> second;

            case DateList(Date firstDate, DateList rest) ->
                    new DateList(
                            firstDate,
                            append(rest, second));
        };
    }
}
