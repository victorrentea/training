using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace VideoStore
{
    internal class Beutor
    {
        public static void Main(string[] args)
        {
            M().GetAwaiter().GetResult();
        }

        public static async Task M()
        {
            var barman = new Barman();


            var stopwatch = Stopwatch.StartNew();

            var comandaDeBere = barman.ToarnaBere();
            var comandaDeTzuica = barman.ToarnaTzuica();

            // Deferred
            await Task.WhenAll(comandaDeBere, comandaDeTzuica);
            var bere = comandaDeBere.Result;
            var tzuica = comandaDeTzuica.Result;

            //Task<Bere> bere = barman.ToarnaBere();
            //Task<Tzuica> tzuica = barman.ToarnaTzuica();
            Console.WriteLine("21A venit comanda. savurez: " + bere + " cu " + tzuica);
            stopwatch.Stop();
            Console.WriteLine($"Took {stopwatch.ElapsedMilliseconds}");
            Console.ReadLine();
        }
    }

    internal class Barman
    {
        public async Task<Bere> ToarnaBere()
        {
            Console.WriteLine("Torn bere");
            await Task.Delay(1000);
            return new Bere();
        }

        public async Task<Tzuica> ToarnaTzuica()
        {
            Console.WriteLine("Torn tzuica");
            await Task.Delay(1000);
            return new Tzuica();
        }
    }

    internal class Bere
    {
    }

    internal class Tzuica
    {
    }
}