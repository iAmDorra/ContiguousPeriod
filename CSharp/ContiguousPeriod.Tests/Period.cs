using System;

namespace ContiguousPeriod.Tests
{
    public class Period : IEquatable<Period>
    {
        public Period(DateTime start, DateTime end, int value)
        {
            this.Start = start;
            this.End = end;
            this.Value = value;
        }

        public DateTime Start { get; private set; }
        public DateTime End { get; private set; }
        public decimal Value { get; private set; }

        public bool Equals(Period other)
        {
            return other != null &&
                this.Start == other.Start &&
                this.End == other.End &&
                this.Value == other.Value;
        }

        internal void UpdateEndDate(DateTime fin)
        {
            this.End = fin;
        }

        public override bool Equals(object obj)
        {
            return this.Equals((Period)obj);
        }
    }
}