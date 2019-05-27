import unittest

class Period :
    def __init__(self):
        return

class TestContiguousPeriod(unittest.TestCase):
    
    def CalculateContiguousPeriods(self, periods):
        return periods;

    def test_should_return_no_period_when_having_empty_collection(self):
        assert self.CalculateContiguousPeriods([]) == []

    def test_should_return_same_period_when_having_one_period(self):
        period = Period()
        assert self.CalculateContiguousPeriods([period]) == [period]
    
if __name__ == '__main__':
    unittest.main()
