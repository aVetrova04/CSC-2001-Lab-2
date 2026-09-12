package main;

public record DateList(Date first, DateList rest) {

    /*
     * DateList is either:
     *
     * null
     *     -> empty list
     *
     * new DateList(first, rest)
     *     -> non-empty list
     */

    /*
     * Purpose: Return the number of dates in the list.
     *
     * Tests:
     *
     * null -> 0
     *
     * [Jan 1] -> 1
     *
     * [Jan 1, Jan 2, Jan 3] -> 3
     */
    public static int listLen(DateList list) {

        switch (list) {
            case null -> {
                return 0;
            }

            default -> {
                return 1 + listLen(list.rest());
            }
        }
    }

    /*
     * Purpose: Return the earliest date in the list.
     * Return null if the list is empty.
     *
     * Tests:
     *
     * null -> null
     *
     * [Jan 5] -> Jan 5
     *
     * [Jan 5, Jan 2, Jan 10] -> Jan 2
     */
    public static Date minDate(DateList list) {

        switch (list) {
            case null -> {
                return null;
            }

            default -> {
                Date restMin = minDate(list.rest());

                if (restMin == null) {
                    return list.first();
                }

                if (Date.comesBefore(list.first(), restMin)) {
                    return list.first();
                }

                return restMin;
            }
        }
    }

    /*
     * Purpose: Return the latest date in the list.
     * Return null if the list is empty.
     *
     * Tests:
     *
     * null -> null
     *
     * [Jan 5] -> Jan 5
     *
     * [Jan 5, Jan 2, Jan 10] -> Jan 10
     */
    public static Date maxDate(DateList list) {

        switch (list) {
            case null -> {
                return null;
            }

            default -> {
                Date restMax = maxDate(list.rest());

                if (restMax == null) {
                    return list.first();
                }

                if (Date.comesBefore(restMax, list.first())) {
                    return list.first();
                }

                return restMax;
            }
        }
    }

    /*
     * Purpose: Return the shortest DateInterval that contains
     * every date in the list.
     * Return null if the list is empty.
     *
     * Tests:
     *
     * null -> null
     *
     * [Jan 5] -> [Jan 5, Jan 5]
     *
     * [Jan 5, Jan 2, Jan 10]
     *     -> [Jan 2, Jan 10]
     */
    public static DateInterval dateCover(DateList list) {

        switch (list) {
            case null -> {
                return null;
            }

            default -> {
                Date min = minDate(list);
                Date max = maxDate(list);

                return new DateInterval(min, max);
            }
        }
    }

    /*
     * Purpose: Return a new list where every date is replaced
     * with the following date.
     *
     * Tests:
     *
     * null -> null
     *
     * [Jan 1] -> [Jan 2]
     *
     * [Jan 1, Jan 5, Jan 10]
     *     -> [Jan 2, Jan 6, Jan 11]
     */
    public static DateList allTomorrows(DateList list) {

        switch (list) {
            case null -> {
                return null;
            }

            default -> {
                return new DateList(
                        Date.tomorrow(list.first()),
                        allTomorrows(list.rest())
                );
            }
        }
    }

    /*
     * Purpose: Return a new list containing all dates from
     * the original list followed by date.
     *
     * Tests:
     *
     * null, Jan 1
     *     -> [Jan 1]
     *
     * [Jan 1, Jan 2], Jan 3
     *     -> [Jan 1, Jan 2, Jan 3]
     */
    public static DateList addToEnd(
            DateList list,
            Date date
    ) {

        switch (list) {
            case null -> {
                return new DateList(date, null);
            }

            default -> {
                return new DateList(
                        list.first(),
                        addToEnd(list.rest(), date)
                );
            }
        }
    }

    /*
     * Purpose: Return a new list containing all dates from
     * list1 followed by all dates from list2.
     *
     * Tests:
     *
     * null, [Jan 1, Jan 2]
     *     -> [Jan 1, Jan 2]
     *
     * [Jan 1, Jan 2], null
     *     -> [Jan 1, Jan 2]
     *
     * [Jan 1, Jan 2], [Jan 3, Jan 4]
     *     -> [Jan 1, Jan 2, Jan 3, Jan 4]
     */
    public static DateList append(
            DateList list1,
            DateList list2
    ) {

        switch (list1) {
            case null -> {
                return list2;
            }

            default -> {
                return new DateList(
                        list1.first(),
                        append(list1.rest(), list2)
                );
            }
        }
    }
}
