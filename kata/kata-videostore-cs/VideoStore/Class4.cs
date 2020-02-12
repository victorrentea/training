using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VideoStore
{

    //delegate void notifyFunc(string e);
    class Joaca
    {
        public static void Main(string[] args)
        {
            Baba baba = new Baba();

            baba.Add(new Persoana());
            baba.Add(new Persoana());

            baba.Afla("Rita cu un pletos");
            Console.ReadLine();
        }
    }

    class Persoana : Interesat
    {
        public void NotifyEvent(string barfa)
        {
            Console.WriteLine("Aflu si eu de " + barfa);
        }
    }

    interface Interesat
    {
        void NotifyEvent(string barfa);
    }
    class Baba
    {
        private List<Interesat> observers = new List<Interesat>();
        
        public void Add(Interesat persoana)
        {
            observers.Add(persoana);
        }
        public void Afla(string barfa)
        {
            foreach (var observer in observers)
            {
                observer.NotifyEvent(barfa);
            }
        }
    }

    


}
