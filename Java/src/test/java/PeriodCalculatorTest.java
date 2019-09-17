import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PeriodCalculatorTest {
    @Test
    public void should_return_the_same_period_when_having_no_one()
    {
        List<Period> input = Collections.emptyList();
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).isEmpty();
    }

    @Test
    public void should_return_one_period_when_having_two_zero_rate_contiguous_periods()
    {
        List<Period> input = Arrays.asList(
                new Period(0, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 2)),
                new Period(0, LocalDate.of(2019, Month.JANUARY, 3), LocalDate.of(2019, Month.JANUARY, 4))
        );
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).hasSize(1);
        Assertions.assertThat(output.get(0).getRate()).isEqualTo(0);
    }

    @Test
    public void should_merge_periods_when_having_two_zero_rate_contiguous_periods()
    {
        LocalDate startDate = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2019, Month.FEBRUARY, 28);

        List<Period> input = Arrays.asList(
                new Period(0, startDate, LocalDate.of(2019, Month.JANUARY, 31)),
                new Period(0, LocalDate.of(2019, Month.FEBRUARY, 1), endDate)
        );
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).containsExactly(new Period(0, startDate, endDate));
    }

    @Test
    public void should_union_only_zero_period(){
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);

        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 31));
        List<Period> input = Arrays.asList(
                nonZeroRatePeriod,
                new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)),
                new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate)
        );
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).containsExactly(nonZeroRatePeriod, new Period(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_periods(){
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);

        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 31));
        Period lastNonZeroPeriod = new Period(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        List<Period> input = Arrays.asList(
                nonZeroRatePeriod,
                new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)),
                new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate),
                lastNonZeroPeriod
        );

        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).containsExactly(nonZeroRatePeriod, lastNonZeroPeriod, new Period(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_contiguous_periods(){
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);

        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 31));
        Period lastNonZeroPeriod = new Period(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        List<Period> input = Arrays.asList(
                nonZeroRatePeriod,
                new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)),
                new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate),
                lastNonZeroPeriod
        );
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).containsExactly(nonZeroRatePeriod, lastNonZeroPeriod, new Period(0, startDate, endDate));
    }

    @Test
    public void should_union_zero_rate_periods(){
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);

        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 31));
        List<Period> input = Arrays.asList(
                nonZeroRatePeriod,
                new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate),
                new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28))
        );
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).containsExactly(nonZeroRatePeriod, new Period(0, startDate, endDate));
    }

    @Test
    public void should_union_only_first_zero_contiguous_periods(){
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);

        Period nonMergedPeriod = new Period(0, LocalDate.of(2019, Month.MAY, 1), LocalDate.of(2019, Month.MAY, 30));
        List<Period> input = Arrays.asList(
                new Period(0, startDate, LocalDate.of(2019, Month.FEBRUARY, 28)),
                new Period(0, LocalDate.of(2019, Month.MARCH, 1), endDate),
                nonMergedPeriod
        );

        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).containsExactly(new Period(0, startDate, endDate), nonMergedPeriod);
    }

    @Test
    public void should_union_only_lastest_zero_contiguous_periods(){
        LocalDate startDate = LocalDate.of(2019, Month.APRIL, 1);
        LocalDate endDate = LocalDate.of(2019, Month.MAY, 30);

        Period nonMergedPeriod = new Period(0, LocalDate.of(2019, Month.FEBRUARY, 1), LocalDate.of(2019, Month.FEBRUARY, 28));
        List<Period> input = Arrays.asList(
                nonMergedPeriod,
                new Period(0, startDate, LocalDate.of(2019, Month.APRIL, 30)),
                new Period(0, LocalDate.of(2019, Month.MAY, 1), endDate)
        );
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        Assertions.assertThat(output).containsExactly(nonMergedPeriod, new Period(0, startDate, endDate));
    }
}
