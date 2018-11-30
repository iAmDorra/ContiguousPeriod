using System;

namespace ContiguousPeriod.Tests
{
    internal class Periode : IEquatable<Periode>
    {
        public Periode(DateTime start, DateTime end, int value)
        {
            this.Debut = start;
            this.Fin = end;
            this.Valeur = value;
        }

        public DateTime Debut { get; private set; }
        public DateTime Fin { get; private set; }
        public decimal Valeur { get; private set; }

        public bool Equals(Periode other)
        {
            return other != null &&
                this.Debut == other.Debut &&
                this.Fin == other.Fin &&
                this.Valeur == other.Valeur;
        }

        public override bool Equals(object obj)
        {
            return this.Equals((Periode)obj);
        }
    }
}