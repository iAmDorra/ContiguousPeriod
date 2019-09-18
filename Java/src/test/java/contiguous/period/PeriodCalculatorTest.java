package contiguous.period;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
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

        assertThat(output.isEmpty()).isEqualTo(true);
    }

    @Test
    public void should_return_one_period_when_having_two_zero_rate_contiguous_periods() {
        List<Period> input = new ArrayList<Period>();
        input.add(new Period(0, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 2)));
        input.add(new Period(0, LocalDate.of(2019, 1, 3), LocalDate.of(2019, 1, 4)));

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output.size()).isEqualTo(1);
        assertThat(output.get(0).getRate()).isEqualTo(0);
    }

    @Test
    public void should_merge_periods_when_having_two_zero_rate_contiguous_periods() {
        List<Period> input = new ArrayList<Period>();
        LocalDate startDate= LocalDate.of(2019,1,1);
        input.add(new Period(
                0,
                startDate,
                LocalDate.of(2019,1,31)));
        LocalDate endDate= LocalDate.of(2019,2,28);
        input.add(new Period(
                0,
                LocalDate.of(2019,2,1),
                endDate));
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output.size()).isEqualTo(1);
        assertThat(output.get(0)).isEqualTo(new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_only_zero_period(){
        List<Period> input = new ArrayList<Period>();
        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate= LocalDate.of(2019,2,1);
        input.add(new Period(0, startDate, LocalDate.of(2019, 2, 28)));
        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Period(0, LocalDate.of(2019, 3, 1), endDate));
        PeriodCalculator calculator = new PeriodCalculator();

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output.size()).isEqualTo(2);
        assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        assertThat(output.get(1)).isEqualTo(new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_zero_periods(){
        List<Period> input = new ArrayList<Period>();
        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new Period(0, startDate, LocalDate.of(2019, 2, 28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Period(0, LocalDate.of(2019, 3, 1), endDate));

        Period lastNonZeroPeriod = new Period(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output.size()).isEqualTo(3);
        assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        assertThat(output.get(1)).isEqualTo(lastNonZeroPeriod);
        assertThat(output.get(2)).isEqualTo(new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_zero_contiguous_periods(){
        List<Period> input = new ArrayList<Period>();
        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);

        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new Period(0, startDate, LocalDate.of(2019, 2, 28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Period(0, LocalDate.of(2019, 3, 1), endDate));

        Period lastNonZeroPeriod = new Period(20, LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 30));
        input.add(lastNonZeroPeriod);

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output.size()).isEqualTo(3);
        assertThat(output.get(0)).isEqualTo(nonZeroRatePeriod);
        assertThat(output.get(1)).isEqualTo(lastNonZeroPeriod);
        assertThat(output.get(2)).isEqualTo(new Period(0, startDate, endDate));
    }

    @Test
    public void Should_union_zero_rate_periods(){
        List<Period> input = new ArrayList<Period>();
        Period nonZeroRatePeriod = new Period(10, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 31));
        input.add(nonZeroRatePeriod);
        LocalDate startDate= LocalDate.of(2019,2,1);
        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Period(0, LocalDate.of(2019, 3, 1), endDate));
        input.add(new Period(0, startDate, LocalDate.of(2019, 2, 28)));

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output.size()).isEqualTo(2);
        assertThat(output.contains(nonZeroRatePeriod)).isTrue();
        assertThat(output.contains(new Period(0, startDate, endDate))).isTrue();
    }

    @Test
    public void Should_union_only_first_zero_contiguous_periods(){
        List<Period> input = new ArrayList<Period>();
        LocalDate startDate = LocalDate.of(2019,2,1);
        input.add(new Period(0, startDate, LocalDate.of(2019, 2, 28)));

        LocalDate endDate= LocalDate.of(2019,3,28);
        input.add(new Period(0, LocalDate.of(2019, 3, 1), endDate));

        Period nonMergedPeriod = new Period(0, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 5, 30));
        input.add(nonMergedPeriod);

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output.size()).isEqualTo(2);
        assertThat(output.contains(new Period(0, startDate, endDate))).isTrue();
        assertThat(output.contains(nonMergedPeriod)).isTrue();
    }

    @Test
    public void Should_union_only_lastest_zero_contiguous_periods(){
        List<Period> input = new ArrayList<Period>();
        Period nonMergedPeriod = new Period(0, LocalDate.of(2019, 2, 1), LocalDate.of(2019, 2, 28));
        input.add(nonMergedPeriod);

        LocalDate startDate = LocalDate.of(2019,4,1);
        input.add(new Period(0, startDate, LocalDate.of(2019, 4, 30)));

        LocalDate endDate = LocalDate.of(2019, 5, 30);
        input.add(new Period(0, LocalDate.of(2019, 5, 1), endDate));

        List<Period> output = calculator.mergeContiguousPeriods(input);

        assertThat(output.size()).isEqualTo(2);
        assertThat(output.contains(new Period(0, startDate, endDate))).isTrue();
        assertThat(output.contains(nonMergedPeriod)).isTrue();
    }
}
