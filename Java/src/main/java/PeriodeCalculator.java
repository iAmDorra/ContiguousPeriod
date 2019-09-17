import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PeriodeCalculator {
    List<Periode> MergeContiguousPeriods(List<Periode> periodes) {
        if(periodes.size()> 1) {
            Stream<Periode> zeroPeriods = periodes.stream()
                    .filter(p1 -> p1.getRate() == 0)
                    .sorted();
            Stream<Periode> mergedPeriods = mergeZeroRateContiguousPeriods(zeroPeriods);
            Stream<Periode> nonZeroRatePeriods = periodes.stream().filter(p -> p.getRate() != 0);
            return Stream.concat(nonZeroRatePeriods, mergedPeriods).collect(Collectors.toList());
        }
        return periodes;
    }

    private Stream<Periode> mergeZeroRateContiguousPeriods(Stream<Periode> zeroPeriods) {
        Periode[] zeroRatePeriods = zeroPeriods.toArray(Periode[]::new);
        List<Periode> mergedPeriods = new ArrayList<>();
        int mergedPeriodIndex = 0;
        mergedPeriods.add(zeroRatePeriods[0]);
        for (int periodIndex = 1; periodIndex < zeroRatePeriods.length; periodIndex++) {
            if (mergedPeriods.get(mergedPeriodIndex).isContiguousTo(zeroRatePeriods[periodIndex])) {
                Periode mergedPeriod =  mergedPeriods.get(mergedPeriodIndex).merge(zeroRatePeriods[periodIndex]);
                mergedPeriods.remove(mergedPeriodIndex);
                mergedPeriods.add(mergedPeriod);
            }
            else {
                mergedPeriods.add(zeroRatePeriods[periodIndex]);
                mergedPeriodIndex++;
            }
        }

        return mergedPeriods.stream();
    }
}