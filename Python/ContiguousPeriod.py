import unittest
from datetime import datetime

class Period :
    def __init__(self, rate, startDate, endDate):
        self.rate = rate
        self.startDate = startDate
        self.endDate = endDate

class PeriodCalculator :
    def CalculateContiguousPeriods(self, periods):
        periodToMerge = []
        periodNotToMerge = []
        i = 0
        while(i < periods.__len__()):
            if(periods[i].rate == 0):
                periodToMerge.append(periods[i])
            else :
                periodNotToMerge.append(periods[i])
            i+=1
                                 
        if(periodToMerge.__len__() > 1):
            periodNotToMerge.insert(0, self.MergePeriods(periodToMerge));
            return periodNotToMerge;
        return periods;

    def MergePeriods(self, periods):
        startDate = periods[0].startDate
        endDate = periods[periods.__len__() - 1].endDate
        mergedPeriod = Period(0, startDate, endDate)
        return mergedPeriod
    
class TestContiguousPeriod(unittest.TestCase):
    def test_should_return_no_period_when_having_empty_collection(self):
        calculator = PeriodCalculator()

        assert calculator.CalculateContiguousPeriods([]) == []

    def test_should_return_same_period_when_having_one_period(self):
        period = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        calculator = PeriodCalculator()

        assert calculator.CalculateContiguousPeriods([period]) == [period]

    def test_should_return_one_period_when_having_two_zero_rate_contiguous_periods(self):
        period1 = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        period2 = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        calculator = PeriodCalculator()

        contiguousPeriod = calculator.CalculateContiguousPeriods([period1, period2])

        assert contiguousPeriod.__len__() == 1
        assert contiguousPeriod[0].rate == period1.rate

    def test_should_merge_periods_when_having_two_zero_rate_contiguous_periods(self):
        startDate = datetime(2019,1,1)
        endDate = datetime(2019,2,28)
        period1 = Period(0,startDate,datetime(2019,1,31))
        period2 = Period(0,datetime(2019,2,1),endDate)
        calculator = PeriodCalculator()
        
        contiguousPeriod = calculator.CalculateContiguousPeriods([period1, period2])

        assert contiguousPeriod.__len__() == 1
        assert contiguousPeriod[0].rate == period1.rate
        assert contiguousPeriod[0].startDate == startDate
        assert contiguousPeriod[0].endDate == endDate
    
    def test_should_not_merge_periods_when_having_different_rates(self):
        period1 = Period(0,datetime(2019,1,1),datetime(2019,1,31))
        period2 = Period(10,datetime(2019,2,1),datetime(2019,2,28))
        calculator = PeriodCalculator()
        
        contiguousPeriod = calculator.CalculateContiguousPeriods([period1, period2])

        assert contiguousPeriod.__len__() == 2
    
    def test_should_merge_only_zero_rate_of_contiguous_periods(self):
        startDate = datetime(2019,1,1)
        endDate = datetime(2019,3,14)
        period1 = Period(0,startDate,datetime(2019,1,31))
        period2 = Period(0,datetime(2019,2,1),endDate)
        period3 = Period(20,datetime(2019,3,15),datetime(2019,4,28))
        calculator = PeriodCalculator()
        
        contiguousPeriod = calculator.CalculateContiguousPeriods([period1, period2, period3])

        assert contiguousPeriod.__len__() == 2
        assert contiguousPeriod[0].startDate == startDate
        assert contiguousPeriod[0].endDate == endDate
        assert contiguousPeriod[1] == period3
    
if __name__ == '__main__':
    unittest.main()
