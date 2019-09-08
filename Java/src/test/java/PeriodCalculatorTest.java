import org.assertj.core.api.Assertions;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class PeriodCalculatorTest {
    @Test
    public void should_return_the_same_period_when_having_no_one()
    {
        List<Periode> input = new ArrayList<Periode>();
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output.isEmpty()).isEqualTo(true);
    }

    @Test
    public void should_return_one_period_when_having_two_zero_rate_contiguous_periods()
    {
        List<Periode> input = new ArrayList<Periode>();
        input.add(new Periode(0));
        input.add(new Periode(0));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.MergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(1);
        Assertions.assertThat(output.get(0).getRate()).isEqualTo(0);
    }
}
