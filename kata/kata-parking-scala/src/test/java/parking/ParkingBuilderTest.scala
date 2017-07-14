package parking

import org.scalatest.FunSuite

class ParkingBuilderTest extends FunSuite {
  test("testBuildBasicParking") {
    val parking = new ParkingBuilder().withSquareSize(4).build();
    assert(16 == parking.getAvailableBays())
  }

  test("testBuildParkingWithPedestrianExit") {
    val parking = new ParkingBuilder().withSquareSize(3).withPedestrianExit(5).build();
    assert(8 == parking.getAvailableBays());
  }

  test("testBuildParkingWithDisabledSlot") {
    val parking = new ParkingBuilder().withSquareSize(2).withDisabledBay(2).build();
    assert(4 == parking.getAvailableBays());
  }

  test("testBuildParkingWithPedestrianExitsAndDisabledSlots") {
    val parking = new ParkingBuilder()
      .withSquareSize(10)
      .withPedestrianExit(8)
      .withPedestrianExit(42)
      .withPedestrianExit(85)
      .withDisabledBay(2)
      .withDisabledBay(47)
      .withDisabledBay(72)
      .build();
    assert(97 == parking.getAvailableBays());
  }
}