import unittest
from datetime import datetime
from dataclasses import dataclass

@dataclass
class Period:
    rate: int
    startDate: datetime
    endDate: datetime
    

class PeriodCalculator :
    def calculate_contiguous_periods(self, periods):
        periodToMerge = []
        periodNotToMerge = []

        for period in periods:
            if(period.rate == 0):
                periodToMerge.append(period)
            else :
                periodNotToMerge.append(period)
                                 
        if(len(periodToMerge) > 1):
            periodNotToMerge.insert(0, self.merge_periods(periodToMerge[0],periodToMerge[-1]));
            return periodNotToMerge;
        return periods;

    def merge_periods(self, period1, period2):
        startDate = period1.startDate
        endDate = period2.endDate
        mergedPeriod = Period(0, startDate, endDate)
        return mergedPeriod
    
class TestContiguousPeriod(unittest.TestCase):
    def test_should_return_no_period_when_having_empty_collection(self):
        calculator = PeriodCalculator()

        self.assertEqual(calculator.calculate_contiguous_periods([]), [])

    def test_should_return_same_period_when_having_one_period(self):
        period = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        calculator = PeriodCalculator()

        self.assertEqual(calculator.calculate_contiguous_periods([period]), [period])

    def test_should_return_one_period_when_having_two_zero_rate_contiguous_periods(self):
        period1 = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        period2 = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        calculator = PeriodCalculator()

        contiguousPeriod = calculator.calculate_contiguous_periods([period1, period2])

        self.assertEqual(len(contiguousPeriod), 1)
        self.assertEqual(contiguousPeriod[0].rate, period1.rate)

    def test_should_merge_periods_when_having_two_zero_rate_contiguous_periods(self):
        startDate = datetime(2019,1,1)
        endDate = datetime(2019,2,28)
        period1 = Period(0,startDate,datetime(2019,1,31))
        period2 = Period(0,datetime(2019,2,1),endDate)
        calculator = PeriodCalculator()
        
        contiguousPeriod = calculator.calculate_contiguous_periods([period1, period2])

        self.assertEqual(len(contiguousPeriod), 1)
        self.assertEqual(contiguousPeriod[0].rate, period1.rate)
        self.assertEqual(contiguousPeriod[0].startDate, startDate)
        self.assertEqual(contiguousPeriod[0].endDate, endDate)
    
    def test_should_not_merge_periods_when_having_different_rates(self):
        period1 = Period(0,datetime(2019,1,1),datetime(2019,1,31))
        period2 = Period(10,datetime(2019,2,1),datetime(2019,2,28))
        calculator = PeriodCalculator()
        
        contiguousPeriod = calculator.calculate_contiguous_periods([period1, period2])

        self.assertEqual(len(contiguousPeriod), 2)
    
    def test_should_merge_only_zero_rate_of_contiguous_periods(self):
        startDate = datetime(2019,1,1)
        endDate = datetime(2019,3,14)
        period1 = Period(0,startDate,datetime(2019,1,31))
        period2 = Period(0,datetime(2019,2,1),endDate)
        period3 = Period(20,datetime(2019,3,15),datetime(2019,4,28))
        calculator = PeriodCalculator()
        
        contiguousPeriod = calculator.calculate_contiguous_periods([period1, period2, period3])

        self.assertEqual(len(contiguousPeriod), 2)
        self.assertEqual(contiguousPeriod[0].startDate, startDate)
        self.assertEqual(contiguousPeriod[0].endDate, endDate)
        self.assertEqual(contiguousPeriod[1], period3)
    
if __name__ == '__main__':
    unittest.main()
