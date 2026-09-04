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


// PURPOSE: Return the number of days in the given month.
    /*
    daysInMonth(1) --> 31
    daysInMonth(2) --> 28
    daysInMonth(9) --> 30
    daysInMonth(12) --> 31
     */

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
            return new Date(1, date.month() + 1, date.year);
        }
        if (date.day() == daysInMonth(date.month()) && date.month == 12) {
            return new Date(1, 1, date.year + 1);
        }
        return null;

    }
}