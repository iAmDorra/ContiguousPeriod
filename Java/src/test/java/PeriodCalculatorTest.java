import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeriodCalculatorTest {
    @Test
    public void should_return_the_no_period_when_having_no_one_to_merge()
    {
        List<RatedPeriod> input = new ArrayList<>();
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).isEmpty();
    }

    @Test
    public void should_return_the_same_period_when_having_only_one_to_merge()
    {
        List<RatedPeriod> input = new ArrayList<>();
        input.add(new RatedPeriod(0, LocalDate.of(2019,1,1), LocalDate.of(2019,1,2)));
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).isEqualTo(input);
    }

    @Test
    public void should_return_one_zero_rated_period_when_having_two_zero_rated_contiguous_periods()
    {
        List<RatedPeriod> input = new ArrayList<>();
        input.add(new RatedPeriod(0,
                LocalDate.of(2019,1,1),
                LocalDate.of(2019,1,2)));
        input.add(new RatedPeriod(0,
                LocalDate.of(2019,1,3),
                LocalDate.of(2019,1,4)));
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(1);
        Assertions.assertThat(output.get(0).IsRateEqualTo(0)).isTrue();
    }

    @Test
    public void should_merge_periods_when_having_two_zero_rated_contiguous_periods()
    {
        List<RatedPeriod> input = new ArrayList<>();
        LocalDate startDate= LocalDate.of(2019,1,1);
        input.add(new RatedPeriod(
                0,
                startDate,
                LocalDate.of(2019,1,31)));
        LocalDate endDate= LocalDate.of(2019,2,28);
        input.add(new RatedPeriod(
                0,
                LocalDate.of(2019,2,1),
                endDate));
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(1);
        Assertions.assertThat(output.get(0)).isEqualTo(new RatedPeriod(0, startDate ,endDate ));
    }

    @Test
    public void should_merge_only_zero_rated_periods(){
        List<RatedPeriod> input = new ArrayList<>();
        RatedPeriod nonZeroRatePeriod = new RatedPeriod(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate= LocalDate.of(2019,2,1);
        input.add(new RatedPeriod(0, startDate, LocalDate.of(2019,2,28)));
        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new RatedPeriod(0, LocalDate.of(2019,3,1), endDate));
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(2);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(new RatedPeriod(0, startDate, endDate));
    }

    @Test
    public void should_merge_zero_periods(){
        List<RatedPeriod> input = new ArrayList<>();
        RatedPeriod nonZeroRatePeriod = new RatedPeriod(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new RatedPeriod(0, startDate, LocalDate.of(2019,2,28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new RatedPeriod(0, LocalDate.of(2019,3,1), endDate));

        RatedPeriod lastNonZeroPeriod = new RatedPeriod(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(3);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(lastNonZeroPeriod);
        Assertions.assertThat(output.get(2)).isEqualTo(new RatedPeriod(0, startDate, endDate));
    }

    @Test
    public void should_merge_zero_contiguous_periods(){
        List<RatedPeriod> input = new ArrayList<>();
        RatedPeriod nonZeroRatePeriod = new RatedPeriod(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new RatedPeriod(0, startDate, LocalDate.of(2019,2,28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new RatedPeriod(0, LocalDate.of(2019,3,1), endDate));

        RatedPeriod lastNonZeroPeriod = new RatedPeriod(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(3);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(lastNonZeroPeriod);
        Assertions.assertThat(output.get(2)).isEqualTo(new RatedPeriod(0, startDate, endDate));
    }

    @Test
    public void should_merge_zero_rate_periods_when_having_mixed_rated_periods(){
        List<RatedPeriod> input = new ArrayList<>();
        RatedPeriod nonZeroRatePeriod = new RatedPeriod(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate= LocalDate.of(2019,2,1);
        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new RatedPeriod(0, LocalDate.of(2019,3,1), endDate));
        input.add(new RatedPeriod(0, startDate, LocalDate.of(2019,2,28)));
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(2);
        Assertions.assertThat(output.contains(nonZeroRatePeriod)).isTrue();
        Assertions.assertThat(output.contains(new RatedPeriod(0, startDate, endDate))).isTrue();
    }

    @Test
    public void Should_merge_only_first_zero_contiguous_periods(){
        List<RatedPeriod> input = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new RatedPeriod(0, startDate, LocalDate.of(2019,2,28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new RatedPeriod(0, LocalDate.of(2019,3,1), endDate));

        RatedPeriod nonMergedPeriod = new RatedPeriod(0, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 5, 30));
        input.add(nonMergedPeriod);
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(2);
        Assertions.assertThat(output.contains(new RatedPeriod(0, startDate, endDate))).isTrue();
        Assertions.assertThat(output.contains(nonMergedPeriod)).isTrue();
    }

    @Test
    public void should_merge_only_latest_zero_contiguous_periods(){
        List<RatedPeriod> input = new ArrayList<>();
        RatedPeriod nonMergedPeriod = new RatedPeriod(0, LocalDate.of(2019,2,1), LocalDate.of(2019,2,28));
        input.add(nonMergedPeriod);

        LocalDate startDate = LocalDate.of(2019,4,1);
        input.add(new RatedPeriod(0, startDate, LocalDate.of(2019,4,30)));

        LocalDate endDate = LocalDate.of(2019, 5, 30);
        input.add(new RatedPeriod(0, LocalDate.of(2019, 5, 1), endDate));
        PeriodCalculator calculator = new PeriodCalculator();

        List<RatedPeriod> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(2);
        Assertions.assertThat(output.contains(new RatedPeriod(0, startDate, endDate))).isTrue();
        Assertions.assertThat(output.contains(nonMergedPeriod)).isTrue();
    }
}
