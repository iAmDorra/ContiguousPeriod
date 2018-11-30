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
        public void Should_return_the_same_period_when_having_only_one()
        {
            var input = new List<Periode>();
            input.Add(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input);

            Check.That(output.First()).IsEqualTo(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
        }

        [TestMethod]
        public void Should_union_all_peiods_when_all_periods_are_zero()
        {
            var input = new List<Periode>();
            input.Add(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 0));
            input.Add(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input);

            Check.That(output.First()).IsEqualTo(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 02, 28), 0));
        }

        [TestMethod]
        public void Should_union_only_zero_period()
        {
            var input = new List<Periode>();
            input.Add(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
            input.Add(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));
            input.Add(new Periode(new DateTime(2018, 03, 01), new DateTime(2018, 03, 31), 0));

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input).ToList();

            Periode firstOutput = output[0];
            Check.That(firstOutput).IsEqualTo(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            Periode secondOutput = output[1];
            Check.That(secondOutput).IsEqualTo(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 03, 31), 0));
        }

        [TestMethod]
        public void Should_union_zero_periods()
        {
            var input = new List<Periode>();
            input.Add(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
            input.Add(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));
            input.Add(new Periode(new DateTime(2018, 03, 01), new DateTime(2018, 03, 31), 0));
            input.Add(new Periode(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input).ToList();

            Periode firstOutput = output[0];
            Check.That(firstOutput).IsEqualTo(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            Periode secondOutput = output[1];
            Check.That(secondOutput).IsEqualTo(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 03, 31), 0));

            Periode thirdPeriod = output[2];
            Check.That(thirdPeriod).IsEqualTo(new Periode(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));
        }

        [TestMethod]
        public void Should_union_one_countiguous_periods()
        {
            var input = new List<Periode>();
            input.Add(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
            input.Add(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));
            input.Add(new Periode(new DateTime(2018, 03, 01), new DateTime(2018, 03, 31), 0));
            input.Add(new Periode(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));
            input.Add(new Periode(new DateTime(2018, 05, 01), new DateTime(2018, 05, 31), 0));

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input).ToList();

            Periode firstOutput = output[0];
            Check.That(firstOutput).IsEqualTo(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            Periode secondOutput = output[1];
            Check.That(secondOutput).IsEqualTo(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 03, 31), 0));

            Periode thirdPeriod = output[2];
            Check.That(thirdPeriod).IsEqualTo(new Periode(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));

            Periode fourthPeriod = output[3];
            Check.That(fourthPeriod).IsEqualTo(new Periode(new DateTime(2018, 05, 01), new DateTime(2018, 05, 31), 0));
        }

        [TestMethod]
        public void Should_union_many_countiguous_periods()
        {
            var input = new List<Periode>();
            input.Add(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
            input.Add(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));
            input.Add(new Periode(new DateTime(2018, 03, 01), new DateTime(2018, 03, 31), 0));
            input.Add(new Periode(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));
            input.Add(new Periode(new DateTime(2018, 05, 01), new DateTime(2018, 05, 31), 0));
            input.Add(new Periode(new DateTime(2018, 06, 01), new DateTime(2018, 06, 30), 0));

            var calculator = new PeriodeCalculator();
            var output = calculator.CalculerPeriodeContigue(input).ToList();

            Periode firstOutput = output[0];
            Check.That(firstOutput).IsEqualTo(new Periode(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            Periode secondOutput = output[1];
            Check.That(secondOutput).IsEqualTo(new Periode(new DateTime(2018, 02, 01), new DateTime(2018, 03, 31), 0));

            Periode thirdPeriod = output[2];
            Check.That(thirdPeriod).IsEqualTo(new Periode(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));

            Periode fourthPeriod = output[3];
            Check.That(fourthPeriod).IsEqualTo(new Periode(new DateTime(2018, 05, 01), new DateTime(2018, 06, 30), 0));
        }
    }
}
