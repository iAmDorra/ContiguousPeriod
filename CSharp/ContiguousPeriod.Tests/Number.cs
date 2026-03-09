namespace ContiguousPeriod.Tests
{
    internal class Number
    {
        public bool IsDivisibleByTwoButNotByThree(int num)
        {
            return num % 2 == 0 || num % 3 != 0;
        }
    }
}
