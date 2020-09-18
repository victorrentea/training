using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VideoStore
{
    //delegate void notifyFunc(string e);
    internal class Joaca
    {
        public static void Main(string[] args)
        {
            var baba = new Baba();

            baba.Add(new Persoana());
            baba.Add(new Persoana());

            baba.Afla("Rita cu un pletos");
            Console.ReadLine();
        }
    }

    internal class Persoana : Interesat
    {
        public void NotifyEvent(string barfa)
        {
            Console.WriteLine("Aflu si eu de " + barfa);
        }
    }

    internal interface Interesat
    {
        void NotifyEvent(string barfa);
    }

    internal class Baba
    {
        private List<Interesat> observers = new List<Interesat>();

        public void Add(Interesat persoana)
        {
            observers.Add(persoana);
        }

        public void Afla(string barfa)
        {
            foreach (var observer in observers) observer.NotifyEvent(barfa);
        }
    }
}