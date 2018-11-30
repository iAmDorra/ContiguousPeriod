using System;

namespace ContiguousPeriod.Tests
{
    internal class Periode : IEquatable<Periode>
    {
        public DateTime Debut { get; set; }
        public DateTime Fin { get; set; }
        public decimal Valeur { get; set; }

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