public record Date(int day, int month, int year) {
    public Date{
        if year < 0:
            throw new IllegalArgumentException("Year not valid.");
        if month <=0 or month > 12:
            throw new IllegalArgumentException("Month not valid");

    }
}

public void main() {
    date1 = Date(30, 11, 1987);
    date2 = Date(12, 3, 1770);
    date3 = Date(9, 1, 0009);
}