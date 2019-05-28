import unittest

class Period :
    def __init__(self, rate):
        self.rate = rate

class TestContiguousPeriod(unittest.TestCase):
    
    def CalculateContiguousPeriods(self, periods):
        if(periods.__len__() > 1):
            return [periods[0]];
        return periods;

    def test_should_return_no_period_when_having_empty_collection(self):
        assert self.CalculateContiguousPeriods([]) == []

    def test_should_return_same_period_when_having_one_period(self):
        period = Period(0)
        assert self.CalculateContiguousPeriods([period]) == [period]

    def test_should_return_one_period_when_having_two_zero_rate_contiguous_periods_(self):
        period1 = Period(0)
        period2 = Period(0)
        contiguousPeriod = self.CalculateContiguousPeriods([period1, period2])
        assert contiguousPeriod.__len__() == 1
        assert contiguousPeriod[0].rate == period1.rate
    
if __name__ == '__main__':
    unittest.main()
