using System;
using System.Collections.Generic;
using System.Linq;
// ReSharper disable PossibleMultipleEnumeration

namespace ContiguousPeriod.Tests
{
    public class PeriodCalculator
    {
        public IEnumerable<Period> CalculateContiguousPeriods(IEnumerable<Period> source)
        {
            var contiguousPeriods = new List<Period>();
            foreach (var period in source.Where(p => p.Value == 0))
            {
                UpdateEndDate(contiguousPeriods, period);
            }

            contiguousPeriods.AddRange(source.Where(p => p.Value != 0));
            return contiguousPeriods.OrderBy(p => p.Start);
        }

        private static void UpdateEndDate(List<Period> contiguousPeriods, Period period)
        {
            var savedZeroPeriod = contiguousPeriods.LastOrDefault(p => p.Value == 0);
            if (savedZeroPeriod != null && period.Start == savedZeroPeriod.End.AddDays(1))
            {
                savedZeroPeriod.UpdateEndDate(period.End);
            }
            else
            {
                var periodStart = period.Start;
                var periodEnd = period.End;
                var value = 0;
                contiguousPeriods.Add(Period.Of(periodStart, periodEnd, value));
            }
        }
    }
}