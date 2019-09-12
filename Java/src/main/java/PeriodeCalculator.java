import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeriodeCalculator {
    public List<Periode> MergeContiguousPeriods(List<Periode> periodes) {
        if(periodes.size()> 1) {
            Periode[] zeroRatePeriods = periodes.stream().filter(p -> p.getRate() == 0).toArray(Periode[]::new);
            Periode firstPeriod = zeroRatePeriods[0];
            Periode lastPeriod = zeroRatePeriods[zeroRatePeriods.length - 1];
            Periode mergedPeriod =  firstPeriod.merge(lastPeriod);
            Stream<Periode> mergedPeriods = Stream.of(mergedPeriod);
            Stream<Periode> nonZeroRatePeriods = periodes.stream().filter(p -> p.getRate() != 0);
            return Stream.concat(nonZeroRatePeriods, mergedPeriods).collect(Collectors.toList());
        }
        return periodes;
    }
}
