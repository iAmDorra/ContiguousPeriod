import java.time.LocalDate;
import java.util.Objects;

public class RatedPeriod {
    private int rate;
    private LocalDate startDate;
    private LocalDate endDate;

    public RatedPeriod(int rate, LocalDate startDate, LocalDate endDate) {
        this.rate = rate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatedPeriod period = (RatedPeriod) o;
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

    public RatedPeriod merge(RatedPeriod other) {
        return new RatedPeriod(this.rate, this.startDate, other.endDate);
    }

    public boolean before(RatedPeriod other) {
        return this.startDate.isBefore(other.startDate);
    }

    public boolean isContiguousTo(RatedPeriod other) {
        return this.endDate.plusDays(1).isEqual(other.startDate);
    }
}
