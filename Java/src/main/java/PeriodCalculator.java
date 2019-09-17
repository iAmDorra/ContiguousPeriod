import java.util.Deque;
import java.util.LinkedList;
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
        Deque<Period> mergedPeriods = new LinkedList<>();
        for (Period currentPeriod : zeroRatePeriods) {
            if (!mergedPeriods.isEmpty() && mergedPeriods.getLast().isContiguousTo(currentPeriod)) {
                Period last = mergedPeriods.removeLast();
                Period mergedPeriod = last.merge(currentPeriod);
                mergedPeriods.addLast(mergedPeriod);
            } else {
                mergedPeriods.addLast(currentPeriod);
            }
        }

        return mergedPeriods.stream();
    }
}