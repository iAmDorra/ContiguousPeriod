using System;

namespace ContiguousPeriod.Tests
{
    internal class Periode : IEquatable<Periode>
    {
        public Periode(DateTime start, DateTime end, int value)
        {
            this.Start = start;
            this.End = end;
            this.Value = value;
        }

        public DateTime Start { get; private set; }
        public DateTime End { get; private set; }
        public decimal Value { get; private set; }

        public bool Equals(Periode other)
        {
            return other != null &&
                this.Start == other.Start &&
                this.End == other.End &&
                this.Value == other.Value;
        }

        internal void updateEndDate(DateTime fin)
        {
            this.End = fin;
        }

        public override bool Equals(object obj)
        {
            return this.Equals((Periode)obj);
        }
    }
}