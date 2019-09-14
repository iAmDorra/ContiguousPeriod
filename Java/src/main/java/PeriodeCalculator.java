import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeriodeCalculator {
    public List<Periode> MergeContiguousPeriods(List<Periode> periodes) {
        if(periodes.size()> 1) {
            Stream<Periode> zeroPeriods = periodes.stream()
                    .filter(p1 -> p1.getRate() == 0)
                    .sorted(getPeriodeComparator());
            Stream<Periode> mergedPeriods = mergeZeroRateContiguousPeriods(zeroPeriods);
            Stream<Periode> nonZeroRatePeriods = periodes.stream().filter(p -> p.getRate() != 0);
            return Stream.concat(nonZeroRatePeriods, mergedPeriods).collect(Collectors.toList());
        }
        return periodes;
    }

    private Comparator<Periode> getPeriodeComparator() {
        return (Periode p1, Periode p2) -> p1.before(p2)? -1: 1;
    }

    private Stream<Periode> mergeZeroRateContiguousPeriods(Stream<Periode> zeroPeriods) {
        Periode[] zeroRatePeriods = zeroPeriods.toArray(Periode[]::new);
        List<Periode> mergedPeriods = new ArrayList<>();
        for (int periodIndex = 0; periodIndex < zeroRatePeriods.length - 1; periodIndex++) {
            if (zeroRatePeriods[periodIndex].isContiguousTo(zeroRatePeriods[periodIndex + 1])) {
                Periode mergedPeriod =  zeroRatePeriods[periodIndex].merge(zeroRatePeriods[periodIndex + 1]);
                mergedPeriods.add(mergedPeriod);
            }
            else {
                mergedPeriods.add(zeroRatePeriods[periodIndex + 1]);
            }
        }

        return mergedPeriods.stream();
    }
}