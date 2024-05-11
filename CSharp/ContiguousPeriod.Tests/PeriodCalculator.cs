using System.Collections.Generic;
using System.Linq;

namespace ContiguousPeriod.Tests
{
    internal class PeriodCalculator
    {
        internal IEnumerable<Period> CalculerPeriodeContigue(IEnumerable<Period> source)
        {
            var periodeContigues = new List<Period>();
            foreach (var periode in source.Where(p => p.Value == 0))
            {
                UpdateEndDate(periodeContigues, periode);
            }

            periodeContigues.AddRange(source.Where(p => p.Value != 0));
            return periodeContigues.OrderBy(p => p.Start);
        }

        private static void UpdateEndDate(List<Period> periodeContigues, Period periode)
        {
            var savedZeroPeriod = periodeContigues.LastOrDefault(p => p.Value == 0);
            if (savedZeroPeriod != null && periode.Start == savedZeroPeriod.End.AddDays(1))
            {
                savedZeroPeriod.updateEndDate(periode.End);
            }
            else
            {
                periodeContigues.Add(new Period(periode.Start, periode.End, 0));
            }
        }
    }
}