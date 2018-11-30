using System.Collections.Generic;
using System.Linq;

namespace ContiguousPeriod.Tests
{
    internal class PeriodeCalculator
    {
        internal IEnumerable<Periode> CalculerPeriodeContigue(IEnumerable<Periode> input)
        {
            if (input.All(p => p.Valeur == 0))
            {
                var start = input.First().Debut;
                var end = input.Last().Fin;
                return new List<Periode> { new Periode(start, end, 0) };
            }
            return input;
        }
    }
}