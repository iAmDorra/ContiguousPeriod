import unittest

class TestContiguousPeriod(unittest.TestCase):
    
    def CalculateContiguousPeriods(self, periods):
        return []

    def test_should_return_no_period_when_having_empty_collection(self):
        assert self.CalculateContiguousPeriods([]) == []
    
if __name__ == '__main__':
    unittest.main()
