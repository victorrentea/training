package parking

import scala.collection.immutable.Set
import scala.collection.immutable.HashSet

class ParkingBuilder0 {
  def withSquareSize(size : Int):ParkingBuilder0 = throw new NotImplementedError
  def withPedestrianExit(index: Int):ParkingBuilder0 = throw new NotImplementedError
  def withDisabledBay(index: Int):ParkingBuilder0 = throw new NotImplementedError
  def build(): Parking0 = throw new NotImplementedError
}

class ParkingBuilder {
  private var size: Int = _;
  private var pedestrianIndexes = new HashSet[Int]
  private var disabledIndexes = new HashSet[Int]
  def withSquareSize(size : Int):ParkingBuilder = {
    this.size = size
    this
  }
  def withPedestrianExit(index: Int):ParkingBuilder = {
    pedestrianIndexes += index
    this
  }
  def withDisabledBay(index: Int):ParkingBuilder =  {
    disabledIndexes += index
    this
  }
  def build(): Parking = new Parking(size, pedestrianIndexes, disabledIndexes)
  
}