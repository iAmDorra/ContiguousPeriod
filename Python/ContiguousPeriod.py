import unittest
from datetime import datetime

class Period :
    def __init__(self, rate, startDate, endDate):
        self.rate = rate
        self.startDate = startDate
        self.endDate = endDate

class TestContiguousPeriod(unittest.TestCase):
    
    def CalculateContiguousPeriods(self, periods):
        if(periods.__len__() > 1):
            return self.MergePeriods(periods);
        return periods;

    def MergePeriods(self, periods):
        startDate = periods[0].startDate
        endDate = periods[periods.__len__() - 1].endDate
        mergedPeriod = Period(0, startDate, endDate)
        return [mergedPeriod]

    def test_should_return_no_period_when_having_empty_collection(self):
        assert self.CalculateContiguousPeriods([]) == []

    def test_should_return_same_period_when_having_one_period(self):
        period = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        assert self.CalculateContiguousPeriods([period]) == [period]

    def test_should_return_one_period_when_having_two_zero_rate_contiguous_periods(self):
        period1 = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        period2 = Period(0, datetime(2019,1,1), datetime(2019,1,31))
        contiguousPeriod = self.CalculateContiguousPeriods([period1, period2])
        assert contiguousPeriod.__len__() == 1
        assert contiguousPeriod[0].rate == period1.rate

    def test_should_merge_periods_when_having_two_zero_rate_contiguous_periods(self):
        startDate = datetime(2019,1,1)
        endDate = datetime(2019,2,28)
        period1 = Period(0,startDate,datetime(2019,1,31))
        period2 = Period(0,datetime(2019,2,1),endDate)
        
        contiguousPeriod = self.CalculateContiguousPeriods([period1, period2])

        assert contiguousPeriod.__len__() == 1
        assert contiguousPeriod[0].rate == period1.rate
        assert contiguousPeriod[0].startDate == startDate
        assert contiguousPeriod[0].endDate == endDate
    
if __name__ == '__main__':
    unittest.main()
