import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PeriodeCalculator {
    public List<Periode> MergeContiguousPeriods(List<Periode> periodes) {
        if(periodes.size()> 1) {
            Periode firstPeriod = periodes.get(0);
            Periode lastPeriod = periodes.get(periodes.size()-1);
            Periode mergedPeriod =  firstPeriod.merge(lastPeriod);
            return Collections.singletonList(mergedPeriod);
        }
        return periodes;
    }
}
