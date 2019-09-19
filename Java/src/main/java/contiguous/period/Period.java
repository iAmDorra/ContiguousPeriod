package contiguous.period;
import java.time.LocalDate;
import java.util.Objects;

public class Period implements Comparable<Period> {
    private int rate;
    private LocalDate startDate;
    private LocalDate endDate;

    public Period(int rate, LocalDate startDate, LocalDate endDate) {
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

    public int getRate() {
        return rate;
    }

    public boolean isRateIsNotZero() {
        return rate != 0;
    }

    public boolean isRateIsZero() {
        return rate == 0;
    }

    public Period merge(Period other) {
        return new Period(this.rate, this.startDate, other.endDate);
    }

    @Override
    public int compareTo(Period other) {
        return this.startDate.compareTo(other.startDate);
    }

    public boolean isContiguousTo(Period other) {
        return this.endDate.plusDays(1).isEqual(other.startDate);
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
