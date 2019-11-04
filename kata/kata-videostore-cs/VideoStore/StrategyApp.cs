using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VideoStore
{
    class StrategyApp
    {
        public static void Main(string[] args)
        {
            CustomsService service = new CustomsService();
            Console.WriteLine("Tax for (RO,100,100) = " + service.ComputeCustomsTax("RO", 100, 100));
            Console.WriteLine("Tax for (CN,100,100) = " + service.ComputeCustomsTax("CN", 100, 100));
            Console.WriteLine("Tax for (UK,100,100) = " + service.ComputeCustomsTax("UK", 100, 100));
            Console.ReadLine();
        }

    }

}

class CustomsService
{
    
    public double ComputeCustomsTax(string originCountry, double tobaccoValue, double regularValue)
    { // UGLY API we CANNOT change
        ITaxProvider pece = SelectTaxProvider(originCountry);
        return pece.Get(tobaccoValue, regularValue);
    }


    private  ITaxProvider SelectTaxProvider(string originCountry)
    {
        ITaxProvider[] taxProviders = {
             new EUTaxProvider(),
            new BrexitTaxProvider() ,
             new ChinaTaxProvider()};
        return taxProviders.ToList().First(tp => tp.Accepts(originCountry));
        //    default: throw new Exception("Not a valid country ISO2 code: " + originCountry);
        
    }
}

interface ITaxProvider
{
    bool Accepts(string originCountry);
    double Get(double tobaccoValue, double regularValue);
}

class EUTaxProvider : ITaxProvider
{
    public bool Accepts(string originCountry)
    {
        return new string[] { "FR", "ES", "RO" }.Contains(originCountry);
    }

    public double Get(double tobaccoValue, double regularValue)
    {
        return tobaccoValue / 3;
    }
}
class BrexitTaxProvider : ITaxProvider
{
    public bool Accepts(string originCountry)
    {
        return originCountry == "UK";
    }

    public double Get(double tobaccoValue, double regularValue)
    {
        return tobaccoValue / 2 + regularValue;
    }
}

class ChinaTaxProvider : ITaxProvider
{
    public bool Accepts(string originCountry)
    {
        return originCountry == "CN";
    }

    public double Get(double tobaccoValue, double regularValue)
    {
  
        return tobaccoValue + regularValue;
    }
}


