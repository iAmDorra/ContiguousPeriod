import java.time.LocalDate;
import java.util.Objects;

public class RatedPeriod {
    private final int rate;
    private final LocalDate startDate;
    private final LocalDate endDate;

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
        return IsRateEqualTo(period.rate) &&
                Objects.equals(startDate, period.startDate) &&
                Objects.equals(endDate, period.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, startDate, endDate);
    }

    public boolean IsRateEqualTo(int rateValue) {
        return rate == rateValue;
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
