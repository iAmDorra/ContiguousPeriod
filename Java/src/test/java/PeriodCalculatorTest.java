import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeriodCalculatorTest {
    @Test
    public void should_return_the_same_period_when_having_no_one()
    {
        List<Periode> input = new ArrayList<>();
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.isEmpty()).isEqualTo(true);
    }

    @Test
    public void should_return_one_period_when_having_two_zero_rate_contiguous_periods()
    {
        List<Periode> input = new ArrayList<>();
        input.add(new Periode(0, LocalDate.of(2019,1,1), LocalDate.of(2019,1,2)));
        input.add(new Periode(0, LocalDate.of(2019,1,3), LocalDate.of(2019,1,4)));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(1);
        Assertions.assertThat(output.get(0).getRate()).isEqualTo(0);
    }

    @Test
    public void should_merge_periods_when_having_two_zero_rate_contiguous_periods()
    {
        List<Periode> input = new ArrayList<>();
        LocalDate startDate= LocalDate.of(2019,1,1);
        input.add(new Periode(
                0,
                startDate,
                LocalDate.of(2019,1,31)));
        LocalDate endDate= LocalDate.of(2019,2,28);
        input.add(new Periode(
                0,
                LocalDate.of(2019,2,1),
                endDate));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(1);
        Assertions.assertThat(output.get(0)).isEqualTo(new Periode(0, startDate ,endDate ));
    }

    @Test
    public void should_union_only_zero_period(){
        List<Periode> input = new ArrayList<>();
        Periode nonZeroRatePeriod = new Periode(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate= LocalDate.of(2019,2,1);
        input.add(new Periode(0, startDate, LocalDate.of(2019,2,28)));
        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Periode(0, LocalDate.of(2019,3,1), endDate));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(2);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(new Periode(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_periods(){
        List<Periode> input = new ArrayList<>();
        Periode nonZeroRatePeriod = new Periode(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new Periode(0, startDate, LocalDate.of(2019,2,28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Periode(0, LocalDate.of(2019,3,1), endDate));

        Periode lastNonZeroPeriod = new Periode(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(3);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(lastNonZeroPeriod);
        Assertions.assertThat(output.get(2)).isEqualTo(new Periode(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_contiguous_periods(){
        List<Periode> input = new ArrayList<>();
        Periode nonZeroRatePeriod = new Periode(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new Periode(0, startDate, LocalDate.of(2019,2,28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Periode(0, LocalDate.of(2019,3,1), endDate));

        Periode lastNonZeroPeriod = new Periode(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(3);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(lastNonZeroPeriod);
        Assertions.assertThat(output.get(2)).isEqualTo(new Periode(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_rate_periods(){
        List<Periode> input = new ArrayList<>();
        Periode nonZeroRatePeriod = new Periode(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate= LocalDate.of(2019,2,1);
        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Periode(0, LocalDate.of(2019,3,1), endDate));
        input.add(new Periode(0, startDate, LocalDate.of(2019,2,28)));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(2);
        Assertions.assertThat(output.contains(nonZeroRatePeriod)).isTrue();
        Assertions.assertThat(output.contains(new Periode(0, startDate, endDate))).isTrue();
    }

    @Test
    public void should_union_only_first_zero_contiguous_periods(){
        List<Periode> input = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new Periode(0, startDate, LocalDate.of(2019,2,28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Periode(0, LocalDate.of(2019,3,1), endDate));

        Periode nonMergedPeriod = new Periode(0, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 5, 30));
        input.add(nonMergedPeriod);
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(2);
        Assertions.assertThat(output.contains(new Periode(0, startDate, endDate))).isTrue();
        Assertions.assertThat(output.contains(nonMergedPeriod)).isTrue();
    }

    @Test
    public void should_union_only_lastest_zero_contiguous_periods(){
        List<Periode> input = new ArrayList<>();
        Periode nonMergedPeriod = new Periode(0, LocalDate.of(2019,2,1), LocalDate.of(2019,2,28));
        input.add(nonMergedPeriod);

        LocalDate startDate = LocalDate.of(2019,4,1);
        input.add(new Periode(0, startDate, LocalDate.of(2019,4,30)));

        LocalDate endDate = LocalDate.of(2019, 5, 30);
        input.add(new Periode(0, LocalDate.of(2019, 5, 1), endDate));
        PeriodeCalculator calculator = new PeriodeCalculator();

        List<Periode> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(2);
        Assertions.assertThat(output.contains(new Periode(0, startDate, endDate))).isTrue();
        Assertions.assertThat(output.contains(nonMergedPeriod)).isTrue();
    }
}
