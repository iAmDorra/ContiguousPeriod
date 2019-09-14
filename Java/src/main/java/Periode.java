import java.time.LocalDate;
import java.util.Objects;

public class Periode {
    private int rate;
    private LocalDate startDate;
    private LocalDate endDate;

    public Periode(int rate, LocalDate startDate, LocalDate endDate) {
        this.rate = rate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periode periode = (Periode) o;
        return rate == periode.rate &&
                Objects.equals(startDate, periode.startDate) &&
                Objects.equals(endDate, periode.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, startDate, endDate);
    }

    public int getRate() {
        return rate;
    }

    public Periode merge(Periode other) {
        return new Periode(this.rate, this.startDate, other.endDate);
    }

    public boolean before(Periode other) {
        return this.startDate.isBefore(other.startDate);
    }

    public boolean isContiguousTo(Periode other) {
        return this.endDate.plusDays(1).isEqual(other.startDate);
    }
}
