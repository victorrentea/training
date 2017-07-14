package parking

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class ParkingTest extends FunSuite with BeforeAndAfter {
  private val FIRSTUPEDESTRIANUEXITUINDEX = 8
  
  var parking:Parking = _;
  
  before {
    println(s"before: ${this.parking}") 
  
    parking = new ParkingBuilder()
      .withSquareSize(5)
      .withPedestrianExit(FIRSTUPEDESTRIANUEXITUINDEX)
      .withPedestrianExit(12)
      .withDisabledBay(5)
      .withDisabledBay(10)
      .build()
  }
  

   test("testGetAvailableBays") {
      assert(23 == parking.getAvailableBays())
  }

  test("testParkCarVehiculeTypeC") {
      assert(7 == parking.parkCar('C'));
      assert(22 == parking.getAvailableBays());
  }

    test("testParkCarVehiculeTypeM") {
        assert(7 == parking.parkCar('M'));
        assert(22 == parking.getAvailableBays());
    }

    test("testParkCarTwoVehicules") {
        assert(7 == parking.parkCar('C'));
        assert(22 == parking.getAvailableBays());

        assert(9 == parking.parkCar('M'));
        assert(21 == parking.getAvailableBays());
    }

    test("testParkCarDisabled") {
        assert(10 == parking.parkCar('D'));
        assert(22 == parking.getAvailableBays());

        assert(5 == parking.parkCar('D'));
        assert(21 == parking.getAvailableBays());

        assert(-1 == parking.parkCar('D'));
        assert(21 == parking.getAvailableBays());
    }

    test("testUnparkCar") {
        val firstCarBayIndex = parking.parkCar('C');
        assert(parking.unparkCar(firstCarBayIndex));
        assert(23 == parking.getAvailableBays());
        assert(! parking.unparkCar(firstCarBayIndex));

        val secondCarBayIndex = parking.parkCar('D');
        assert(parking.unparkCar(secondCarBayIndex));
        assert(23 == parking.getAvailableBays());
        assert(! parking.unparkCar(secondCarBayIndex));

        assert(! parking.unparkCar(FIRSTUPEDESTRIANUEXITUINDEX));
//        val d = new DisabledSlot(1)
//        d.free
    }

    test("testToString") {
        assert("UUUUU\nU=UU@\n@U=UU\nUUUUU\nUUUUU" == parking.toString());
    }

    test("testCompleteSolution") {
        assert(7 == parking.parkCar('C'));
        assert("UUUUU\nU=CU@\n@U=UU\nUUUUU\nUUUUU" == parking.toString());
        assert(22 == parking.getAvailableBays());

        assert(9 == parking.parkCar('C'));
        assert("UUUUU\nC=CU@\n@U=UU\nUUUUU\nUUUUU" == parking.toString());
        assert(21 == parking.getAvailableBays());

        assert(11 == parking.parkCar('M'));
        assert("UUUUU\nC=CU@\n@M=UU\nUUUUU\nUUUUU" == parking.toString());
        assert(20 == parking.getAvailableBays());

        assert(13 == parking.parkCar('M'));
        assert("UUUUU\nC=CU@\n@M=MU\nUUUUU\nUUUUU" == parking.toString());
        assert(19 == parking.getAvailableBays());

        assert(10 == parking.parkCar('D'));
        assert("UUUUU\nC=CU@\nDM=MU\nUUUUU\nUUUUU" == parking.toString());
        assert(18 == parking.getAvailableBays());

        assert(5 == parking.parkCar('D'));
        assert("UUUUU\nC=CUD\nDM=MU\nUUUUU\nUUUUU" == parking.toString());
        assert(17 == parking.getAvailableBays());

        assert(-1 == parking.parkCar('D'));
        assert("UUUUU\nC=CUD\nDM=MU\nUUUUU\nUUUUU" == parking.toString());
        assert(17 == parking.getAvailableBays());

        assert(!parking.unparkCar(3));
        assert("UUUUU\nC=CUD\nDM=MU\nUUUUU\nUUUUU" == parking.toString());
        assert(17 == parking.getAvailableBays());

        assert(parking.unparkCar(13));
        assert("UUUUU\nC=CUD\nDM=UU\nUUUUU\nUUUUU" == parking.toString());
        assert(18 == parking.getAvailableBays());
    }
}