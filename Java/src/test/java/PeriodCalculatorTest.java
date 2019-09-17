import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PeriodCalculatorTest {
    @Test
    public void should_return_the_same_period_when_having_no_one()
    {
        List<Period> input = new ArrayList<>();
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.isEmpty()).isEqualTo(true);
    }

    @Test
    public void should_return_one_period_when_having_two_zero_rate_contiguous_periods()
    {
        List<Period> input = new ArrayList<>();
        input.add(new Period(0, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 2)));
        input.add(new Period(0, LocalDate.of(2019, Month.JANUARY, 3), LocalDate.of(2019, Month.JANUARY, 4)));
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(1);
        Assertions.assertThat(output.get(0).getRate()).isEqualTo(0);
    }

    @Test
    public void should_merge_periods_when_having_two_zero_rate_contiguous_periods()
    {
        List<Period> input = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2019, Month.JANUARY, 1);
        input.add(new Period(
                0,
                startDate,
                LocalDate.of(2019, Month.JANUARY, 31)));
        LocalDate endDate = LocalDate.of(2019, Month.FEBRUARY, 28);
        input.add(new Period(
                0,
                LocalDate.of(2019, Month.FEBRUARY, 1),
                endDate));
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(1);
        Assertions.assertThat(output.get(0)).isEqualTo(new Period(0, startDate ,endDate ));
    }

    @Test
    public void should_union_only_zero_period(){
        List<Period> input = new ArrayList<>();
        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        input.add(new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)));
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate));
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(2);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(new Period(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_periods(){
        List<Period> input = new ArrayList<>();
        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        input.add(new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)));

        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate));

        Period lastNonZeroPeriod = new Period(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(3);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(lastNonZeroPeriod);
        Assertions.assertThat(output.get(2)).isEqualTo(new Period(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_contiguous_periods(){
        List<Period> input = new ArrayList<>();
        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        input.add(new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)));

        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate));

        Period lastNonZeroPeriod = new Period(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(3);
        Assertions.assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        Assertions.assertThat(output.get(1)).isEqualTo(lastNonZeroPeriod);
        Assertions.assertThat(output.get(2)).isEqualTo(new Period(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_rate_periods(){
        List<Period> input = new ArrayList<>();
        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate));
        input.add(new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)));
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(2);
        Assertions.assertThat(output.contains(nonZeroRatePeriod)).isTrue();
        Assertions.assertThat(output.contains(new Period(0, startDate, endDate))).isTrue();
    }

    @Test
    public void should_union_only_first_zero_contiguous_periods(){
        List<Period> input = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        input.add(new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)));

        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate));

        Period nonMergedPeriod = new Period(0, LocalDate.of(2019, Month.MAY, 1), LocalDate.of(2019, Month.MAY, 30));
        input.add(nonMergedPeriod);
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(2);
        Assertions.assertThat(output.contains(new Period(0, startDate, endDate))).isTrue();
        Assertions.assertThat(output.contains(nonMergedPeriod)).isTrue();
    }

    @Test
    public void should_union_only_lastest_zero_contiguous_periods(){
        List<Period> input = new ArrayList<>();
        Period nonMergedPeriod = new Period(0, LocalDate.of(2019, Month.FEBRUARY, 1), LocalDate.of(2019, Month.FEBRUARY, 28));
        input.add(nonMergedPeriod);

        LocalDate startDate = LocalDate.of(2019, Month.APRIL, 1);
        input.add(new Period(0, startDate, LocalDate.of(2019, Month.APRIL, 30)));

        LocalDate endDate = LocalDate.of(2019, Month.MAY, 30);
        input.add(new Period(0, LocalDate.of(2019, Month.MAY, 1), endDate));
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output.size()).isEqualTo(2);
        Assertions.assertThat(output.contains(new Period(0, startDate, endDate))).isTrue();
        Assertions.assertThat(output.contains(nonMergedPeriod)).isTrue();
    }
}
