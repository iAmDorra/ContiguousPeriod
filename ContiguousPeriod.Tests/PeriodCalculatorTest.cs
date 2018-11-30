using System;
using System.Linq;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using NFluent;

namespace ContiguousPeriod.Tests
{
    [TestClass]
    public class PeriodCalculatorTest
    {
        [TestMethod]
        public void Calcul_une_seule_periode()
        {
            var input = new List<Periode>();
            input.Add(new Periode() { Debut = new DateTime(2018, 01, 01), Fin = new DateTime(2018, 01, 31), Valeur = 10 });

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input);

            Check.That(output.First()).IsEqualTo(new Periode()
            { Debut = new DateTime(2018, 01, 01), Fin = new DateTime(2018, 01, 31), Valeur = 10 });
        }

        [TestMethod]
        public void Calcul_periode_contigue()
        {
            var input = new List<Periode>();
            input.Add(new Periode() { Debut = new DateTime(2018, 01, 01), Fin = new DateTime(2018, 01, 31), Valeur = 0 });
            input.Add(new Periode() { Debut = new DateTime(2018, 02, 01), Fin = new DateTime(2018, 02, 28), Valeur = 0 });

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input);

            Check.That(output.First()).IsEqualTo(new Periode()
            { Debut = new DateTime(2018, 01, 01), Fin = new DateTime(2018, 02, 28), Valeur = 0 });
        }

    }
}
