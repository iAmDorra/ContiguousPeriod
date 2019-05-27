using System.Collections.Generic;
using System.Linq;

namespace ContiguousPeriod.Tests
{
    internal class PeriodeCalculator
    {
        internal IEnumerable<Periode> CalculerPeriodeContigue(IEnumerable<Periode> source)
        {
            var periodeContigues = new List<Periode>();
            foreach (var periode in source.Where(p => p.Value == 0))
            {
                UpdateEndDate(periodeContigues, periode);
            }

            periodeContigues.AddRange(source.Where(p => p.Value != 0));
            return periodeContigues.OrderBy(p => p.Start);
        }

        private static void UpdateEndDate(List<Periode> periodeContigues, Periode periode)
        {
            var savedZeroPeriod = periodeContigues.LastOrDefault(p => p.Value == 0);
            if (savedZeroPeriod != null && periode.Start == savedZeroPeriod.End.AddDays(1))
            {
                savedZeroPeriod.updateEndDate(periode.End);
            }
            else
            {
                periodeContigues.Add(new Periode(periode.Start, periode.End, 0));
            }
        }
    }
}