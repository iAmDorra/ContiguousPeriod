package contiguous.period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeriodCalculator {
    public List<Period> mergeContiguousPeriods(List<Period> periods) {
        if (periods.size() > 1) {
            Stream<Period> zeroPeriods = periods.stream()
                    .filter(p1 -> p1.getRate() == 0)
                    .sorted(getPeriodeComparator());
            Stream<Period> mergedPeriods = mergeZeroRateContiguousPeriods(zeroPeriods);
            Stream<Period> nonZeroRatePeriods = periods.stream().filter(p -> p.getRate() != 0);
            return Stream.concat(nonZeroRatePeriods, mergedPeriods).collect(Collectors.toList());
        }
        return periods;
    }

    private Comparator<Period> getPeriodeComparator() {
        return (Period p1, Period p2) -> p1.before(p2) ? -1 : 1;
    }

    private Stream<Period> mergeZeroRateContiguousPeriods(Stream<Period> zeroPeriods) {
        Period[] zeroRatePeriods = zeroPeriods.toArray(Period[]::new);
        List<Period> mergedPeriods = new ArrayList<>();
        int mergedPeriodIndex = 0;
        mergedPeriods.add(zeroRatePeriods[0]);
        for (int periodIndex = 1; periodIndex < zeroRatePeriods.length; periodIndex++) {
            if (mergedPeriods.get(mergedPeriodIndex).isContiguousTo(zeroRatePeriods[periodIndex])) {
                Period mergedPeriod = mergedPeriods.get(mergedPeriodIndex).merge(zeroRatePeriods[periodIndex]);
                mergedPeriods.remove(mergedPeriodIndex);
                mergedPeriods.add(mergedPeriod);
            } else {
                mergedPeriods.add(zeroRatePeriods[periodIndex]);
                mergedPeriodIndex++;
            }
        }

        return mergedPeriods.stream();
    }
}