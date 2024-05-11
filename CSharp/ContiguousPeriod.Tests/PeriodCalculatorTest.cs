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
            var input = new List<Period>();
            input.Add(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            var calculator = new PeriodCalculator();
            var output = calculator.CalculateContiguousPeriods(input);

            Check.That(output.First()).IsEqualTo(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
        }

        [TestMethod]
        public void Should_union_all_periods_when_all_periods_are_zero()
        {
            var input = new List<Period>();
            input.Add(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 0));
            input.Add(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));

            var calculator = new PeriodCalculator();
            var output = calculator.CalculateContiguousPeriods(input);

            Check.That(output.First()).IsEqualTo(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 02, 28), 0));
        }

        [TestMethod]
        public void Should_union_only_zero_period()
        {
            var input = new List<Period>();
            input.Add(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
            input.Add(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));
            input.Add(new Period(new DateTime(2018, 03, 01), new DateTime(2018, 03, 31), 0));

            var calculator = new PeriodCalculator();
            var output = calculator.CalculateContiguousPeriods(input).ToList();

            Period firstOutput = output[0];
            Check.That(firstOutput).IsEqualTo(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            Period secondOutput = output[1];
            Check.That(secondOutput).IsEqualTo(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 03, 31), 0));
        }

        [TestMethod]
        public void Should_union_zero_periods()
        {
            var input = new List<Period>();
            input.Add(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
            input.Add(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));
            input.Add(new Period(new DateTime(2018, 03, 01), new DateTime(2018, 03, 31), 0));
            input.Add(new Period(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));

            var calculator = new PeriodCalculator();
            var output = calculator.CalculateContiguousPeriods(input).ToList();

            Period firstOutput = output[0];
            Check.That(firstOutput).IsEqualTo(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            Period secondOutput = output[1];
            Check.That(secondOutput).IsEqualTo(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 03, 31), 0));

            Period thirdPeriod = output[2];
            Check.That(thirdPeriod).IsEqualTo(new Period(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));
        }

        [TestMethod]
        public void Should_union_one_contiguous_periods()
        {
            var input = new List<Period>();
            input.Add(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
            input.Add(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));
            input.Add(new Period(new DateTime(2018, 03, 01), new DateTime(2018, 03, 31), 0));
            input.Add(new Period(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));
            input.Add(new Period(new DateTime(2018, 05, 01), new DateTime(2018, 05, 31), 0));

            var calculator = new PeriodCalculator();
            var output = calculator.CalculateContiguousPeriods(input).ToList();

            Period firstOutput = output[0];
            Check.That(firstOutput).IsEqualTo(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            Period secondOutput = output[1];
            Check.That(secondOutput).IsEqualTo(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 03, 31), 0));

            Period thirdPeriod = output[2];
            Check.That(thirdPeriod).IsEqualTo(new Period(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));

            Period fourthPeriod = output[3];
            Check.That(fourthPeriod).IsEqualTo(new Period(new DateTime(2018, 05, 01), new DateTime(2018, 05, 31), 0));
        }

        [TestMethod]
        public void Should_union_many_contiguous_periods()
        {
            var input = new List<Period>();
            input.Add(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));
            input.Add(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 02, 28), 0));
            input.Add(new Period(new DateTime(2018, 03, 01), new DateTime(2018, 03, 31), 0));
            input.Add(new Period(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));
            input.Add(new Period(new DateTime(2018, 05, 01), new DateTime(2018, 05, 31), 0));
            input.Add(new Period(new DateTime(2018, 06, 01), new DateTime(2018, 06, 30), 0));

            var calculator = new PeriodCalculator();
            var output = calculator.CalculateContiguousPeriods(input).ToList();

            Period firstOutput = output[0];
            Check.That(firstOutput).IsEqualTo(new Period(new DateTime(2018, 01, 01), new DateTime(2018, 01, 31), 10));

            Period secondOutput = output[1];
            Check.That(secondOutput).IsEqualTo(new Period(new DateTime(2018, 02, 01), new DateTime(2018, 03, 31), 0));

            Period thirdPeriod = output[2];
            Check.That(thirdPeriod).IsEqualTo(new Period(new DateTime(2018, 04, 01), new DateTime(2018, 04, 30), 20));

            Period fourthPeriod = output[3];
            Check.That(fourthPeriod).IsEqualTo(new Period(new DateTime(2018, 05, 01), new DateTime(2018, 06, 30), 0));
        }
    }
}
