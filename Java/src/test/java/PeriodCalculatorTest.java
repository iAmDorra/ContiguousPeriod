import org.assertj.core.api.Assertions;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class PeriodCalculatorTest {
    @Test
    public void Should_return_the_same_period_when_having_no_one()
    {
        List<Periode> input = new ArrayList<Periode>();
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output.isEmpty()).isEqualTo(true);
    }
}
