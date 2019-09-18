package contiguous.period;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class PeriodCalculatorTest {

    private static final List<Period> NO_PERIOD = emptyList();

    private PeriodCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new PeriodCalculator();
    }

    @Test
    public void should_return_the_same_period_when_having_no_one() {
        List<Period> input = NO_PERIOD;

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).isEmpty();
    }

    @Test
    public void should_return_one_period_when_having_two_zero_rate_contiguous_periods() {
        List<Period> input = new ArrayList<Period>();
        input.add(new Period(0,
                LocalDate.of(2019, Month.JANUARY, 1),
                LocalDate.of(2019, Month.JANUARY, 2)));
        input.add(new Period(0,
                LocalDate.of(2019, Month.JANUARY, 3),
                LocalDate.of(2019, Month.JANUARY, 4)));

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).hasSize(1);
        assertThat(output).allMatch(period -> period.getRate() == 0);
    }

    @Test
    public void should_merge_periods_when_having_two_zero_rate_contiguous_periods() {
        List<Period> input = new ArrayList<Period>();
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

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).hasSize(1);
        assertThat(output).containsExactly(new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_only_zero_period() {
        List<Period> input = new ArrayList<Period>();
        Period nonZeroRatePeriod = new Period(
                10,
                LocalDate.of(2019, Month.JANUARY, 1),
                LocalDate.of(2019, Month.JANUARY, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        input.add(new Period(
                0,
                startDate,
                LocalDate.of(2019, Month.FEBRUARY, 28)));
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(
                0,
                LocalDate.of(2019, Month.MARCH, 1),
                endDate));

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).hasSize(2);
        assertThat(output).containsExactly(nonZeroRatePeriod, new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_zero_periods() {
        List<Period> input = new ArrayList<Period>();
        Period nonZeroRatePeriod = new Period(
                10,
                LocalDate.of(2019, Month.JANUARY, 1),
                LocalDate.of(2019, Month.JANUARY, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        input.add(new Period(
                0,
                startDate,
                LocalDate.of(2019, Month.FEBRUARY, 28)));

        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(
                0,
                LocalDate.of(2019, Month.MARCH, 1),
                endDate));

        Period lastNonZeroPeriod = new Period(
                20,
                LocalDate.of(2019, Month.APRIL, 1),
                LocalDate.of(2019, Month.APRIL, 30));
        input.add(lastNonZeroPeriod);

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).hasSize(3);
        assertThat(output).containsExactly(nonZeroRatePeriod, lastNonZeroPeriod, new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_zero_contiguous_periods() {
        List<Period> input = new ArrayList<Period>();
        Period nonZeroRatePeriod = new Period(
                10,
                LocalDate.of(2019, Month.JANUARY, 1),
                LocalDate.of(2019, Month.JANUARY, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        input.add(new Period(
                0,
                startDate,
                LocalDate.of(2019, Month.FEBRUARY, 28)));

        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(
                0,
                LocalDate.of(2019, Month.MARCH, 1),
                endDate));

        Period lastNonZeroPeriod = new Period(
                20,
                LocalDate.of(2019, Month.APRIL, 1),
                LocalDate.of(2019, Month.APRIL, 30));
        input.add(lastNonZeroPeriod);

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).hasSize(3);
        assertThat(output).containsExactly(nonZeroRatePeriod, lastNonZeroPeriod, new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_zero_rate_periods() {
        List<Period> input = new ArrayList<Period>();
        Period nonZeroRatePeriod = new Period(
                10,
                LocalDate.of(2019, Month.JANUARY, 1),
                LocalDate.of(2019, Month.JANUARY, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(
                0,
                LocalDate.of(2019, Month.MARCH, 1),
                endDate));
        input.add(new Period(
                0,
                startDate,
                LocalDate.of(2019, Month.FEBRUARY, 28)));

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).hasSize(2);
        assertThat(output).containsExactly(nonZeroRatePeriod, new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_only_first_zero_contiguous_periods() {
        List<Period> input = new ArrayList<Period>();
        LocalDate startDate = LocalDate.of(2019, Month.FEBRUARY, 1);
        input.add(new Period(
                0,
                startDate,
                LocalDate.of(2019, 2, 28)));

        LocalDate endDate = LocalDate.of(2019, Month.MARCH, 28);
        input.add(new Period(
                0,
                LocalDate.of(2019, Month.MARCH, 1),
                endDate));

        Period nonMergedPeriod = new Period(
                0,
                LocalDate.of(2019, Month.MAY, 1),
                LocalDate.of(2019, Month.MAY, 30));
        input.add(nonMergedPeriod);

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).hasSize(2);
        assertThat(output).containsExactly(new Period(0, startDate, endDate), nonMergedPeriod);
    }

    @Test
    public void Should_union_only_lastest_zero_contiguous_periods() {
        List<Period> input = new ArrayList<Period>();
        Period nonMergedPeriod = new Period(
                0,
                LocalDate.of(2019, Month.FEBRUARY, 1),
                LocalDate.of(2019, Month.FEBRUARY, 28));
        input.add(nonMergedPeriod);

        LocalDate startDate = LocalDate.of(2019, Month.APRIL, 1);
        input.add(new Period(
                0,
                startDate,
                LocalDate.of(2019, Month.APRIL, 30)));

        LocalDate endDate = LocalDate.of(2019, Month.MAY, 30);
        input.add(new Period(
                0,
                LocalDate.of(2019, Month.MAY, 1),
                endDate));

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output).hasSize(2);
        assertThat(output).containsOnly(new Period(0, startDate, endDate), nonMergedPeriod);
    }
}
