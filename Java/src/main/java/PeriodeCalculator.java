import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PeriodeCalculator {

    List<Periode> mergeContiguousPeriods(List<Periode> periodes) {
        if(periodes.size()> 1) {
            List<Periode> zeroPeriods = periodes.stream()
                    .filter(p1 -> p1.getRate() == 0)
                    .sorted()
                    .collect(Collectors.toList());
            Stream<Periode> mergedPeriods = mergeZeroRateContiguousPeriods(zeroPeriods);
            Stream<Periode> nonZeroRatePeriods = periodes.stream().filter(p -> p.getRate() != 0);
            return Stream.concat(nonZeroRatePeriods, mergedPeriods).collect(Collectors.toList());
        }
        return periodes;
    }

    private Stream<Periode> mergeZeroRateContiguousPeriods(List<Periode> zeroRatePeriods) {
        List<Periode> mergedPeriods = new ArrayList<>();
        mergedPeriods.add(zeroRatePeriods.get(0));
        int mergedPeriodIndex = 0;
        for (int periodIndex = 1; periodIndex < zeroRatePeriods.size(); periodIndex++) {
            Periode currentPeriod = zeroRatePeriods.get(periodIndex);
            if (mergedPeriods.get(mergedPeriodIndex).isContiguousTo(currentPeriod)) {
                Periode mergedPeriod =  mergedPeriods.get(mergedPeriodIndex).merge(currentPeriod);
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