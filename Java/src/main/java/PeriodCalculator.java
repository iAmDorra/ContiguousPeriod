import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PeriodCalculator {

    List<Period> mergeContiguousPeriods(List<Period> periods) {
        if (periods.size() <= 1) {
            return periods;
        }
        List<Period> zeroPeriods = periods.stream()
                .filter(p1 -> p1.getRate() == 0)
                .sorted()
                .collect(Collectors.toList());
        Stream<Period> mergedPeriods = mergeZeroRateContiguousPeriods(zeroPeriods);
        Stream<Period> nonZeroRatePeriods = periods.stream().filter(p -> p.getRate() != 0);
        return Stream.concat(nonZeroRatePeriods, mergedPeriods).collect(Collectors.toList());
    }

    private Stream<Period> mergeZeroRateContiguousPeriods(List<Period> zeroRatePeriods) {
        List<Period> mergedPeriods = new ArrayList<>();
        mergedPeriods.add(zeroRatePeriods.get(0));
        int mergedPeriodIndex = 0;
        for (int periodIndex = 1; periodIndex < zeroRatePeriods.size(); periodIndex++) {
            Period currentPeriod = zeroRatePeriods.get(periodIndex);
            if (mergedPeriods.get(mergedPeriodIndex).isContiguousTo(currentPeriod)) {
                Period mergedPeriod =  mergedPeriods.get(mergedPeriodIndex).merge(currentPeriod);
                mergedPeriods.remove(mergedPeriodIndex);
                mergedPeriods.add(mergedPeriod);
            }
            else {
                mergedPeriods.add(currentPeriod);
                mergedPeriodIndex++;
            }
        }

        return mergedPeriods.stream();
    }
}