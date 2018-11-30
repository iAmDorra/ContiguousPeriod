using Microsoft.VisualStudio.TestTools.UnitTesting;
using NFluent;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ContiguousPeriod.Tests
{
    [TestClass]
    public class PeriodCalculatorTest
    {
        [TestMethod]
        public void Calcul_une_seule_periode()
        {
            var input = new List<Periode>();
            input.Add(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input);

            Check.That(output.First()).IsEqualTo(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
        }

        [TestMethod]
        public void Calcul_periode_contigue()
        {
            var input = new List<Periode>();
            input.Add(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 0));
            input.Add(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input);

            Check.That(output.First()).IsEqualTo(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 02, 28), 0));
        }

    }
}
