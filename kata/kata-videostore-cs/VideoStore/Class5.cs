using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace VideoStore
{
    class Beutor
    {
        public static void Main(string[] args)
        {
            M().GetAwaiter().GetResult();
        }
        public async static Task M() 
        {
            Barman barman = new Barman();


            Stopwatch stopwatch = Stopwatch.StartNew();

            Task<Bere> comandaDeBere = barman.ToarnaBere();
            Task<Tzuica> comandaDeTzuica = barman.ToarnaTzuica();

            // Deferred
            await Task.WhenAll(comandaDeBere, comandaDeTzuica);
            Bere bere = comandaDeBere.Result;
            Tzuica tzuica = comandaDeTzuica.Result;

            //Task<Bere> bere = barman.ToarnaBere();
            //Task<Tzuica> tzuica = barman.ToarnaTzuica();
            Console.WriteLine("21A venit comanda. savurez: " + bere + " cu " + tzuica);
            stopwatch.Stop();
            Console.WriteLine($"Took {stopwatch.ElapsedMilliseconds}");
            Console.ReadLine();
        }
    }

    class Barman
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
    class Bere { }
    class Tzuica { }
}
