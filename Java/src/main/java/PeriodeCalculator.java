import java.util.Collections;
import java.util.List;

public class PeriodeCalculator {
    public List<Periode> MergeContiguousPeriods(List<Periode> periodes) {
        if(periodes.size()> 1)
            return Collections.singletonList(periodes.get(0));
        return periodes;
    }
}
