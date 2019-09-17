import java.time.LocalDate;
import java.util.Objects;

public final class Period implements Comparable<Period> {
    private final int rate;
    private final LocalDate startDate;
    private final LocalDate endDate;

    Period(int rate, LocalDate startDate, LocalDate endDate) {
        this.rate = rate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return rate == period.rate &&
                Objects.equals(startDate, period.startDate) &&
                Objects.equals(endDate, period.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, startDate, endDate);
    }

    int getRate() {
        return rate;
    }

    Period merge(Period other) {
        return new Period(this.rate, this.startDate, other.endDate);
    }

    boolean isContiguousTo(Period other) {
        return this.endDate.plusDays(1).isEqual(other.startDate);
    }

    @Override
    public int compareTo(Period other) {
        return this.startDate.compareTo(other.startDate);
    }

    @Override
    public String toString() {
        return "Period{" +
                "rate=" + rate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
