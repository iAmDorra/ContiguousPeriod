import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeriodCalculator {
    public List<RatedPeriod> mergeContiguousPeriods(List<RatedPeriod> periods) {
        if(periods.size()> 1) {
            Stream<RatedPeriod> zeroPeriods = periods.stream()
                    .filter(p1 -> p1.IsRateEqualTo(0))
                    .sorted(getPeriodComparator());
            Stream<RatedPeriod> mergedPeriods = mergePeriods(zeroPeriods);
            Stream<RatedPeriod> nonZeroRatePeriods = periods.stream().filter(p -> !p.IsRateEqualTo(0));
            return Stream.concat(nonZeroRatePeriods, mergedPeriods).collect(Collectors.toList());
        }
        return periods;
    }

    private Comparator<RatedPeriod> getPeriodComparator() {

        return (RatedPeriod p1, RatedPeriod p2) -> p1.before(p2)? -1: 1;
    }

    private Stream<RatedPeriod> mergePeriods(Stream<RatedPeriod> periods) {
        RatedPeriod[] ratedPeriods = periods.toArray(RatedPeriod[]::new);
        List<RatedPeriod> mergedPeriods = new ArrayList<>();
        int mergedPeriodIndex = 0;
        mergedPeriods.add(ratedPeriods[0]);
        for (int periodIndex = 1; periodIndex < ratedPeriods.length; periodIndex++) {
            if (mergedPeriods.get(mergedPeriodIndex).isContiguousTo(ratedPeriods[periodIndex])) {
                RatedPeriod mergedPeriod =  mergedPeriods.get(mergedPeriodIndex).merge(ratedPeriods[periodIndex]);
                mergedPeriods.set(mergedPeriodIndex, mergedPeriod);
            }
            else {
                mergedPeriods.add(ratedPeriods[periodIndex]);
                mergedPeriodIndex++;
            }
        }

        return mergedPeriods.stream();
    }
}