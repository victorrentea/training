package parking

class Parking0 {
  def getAvailableBays(): Int = throw new NotImplementedError
  def parkCar(char: Char): Int = throw new NotImplementedError
  def unparkCar(index: Int): Boolean = throw new NotImplementedError
}

class Parking(private val size:Int, private val pedestrianIndexes:Set[Int], disabledIndexes:Set[Int]) {
//  val slots = Array.fill[ParkingSlot](size * size)(new NormalSlot())
  val slots = new Array[ParkingSlot](size * size)
  
  for (gi <- 0 to slots.length - 1) slots(gi) = new NormalSlot(gi)
  for (pi <- pedestrianIndexes) slots(pi) = new PedestrianSlot(pi)
  for (pi <- disabledIndexes) slots(pi) = new DisabledSlot(pi)
  
  def getAvailableBays(): Int = {
    slots.count(_.isFree)
  }
  def parkCar(carType: Char): Int = {
    val isDisabled = carType == 'D'
    val allFreeSlots = slots
      .filter(_.isFree)
      .filter(_.isDisabled == isDisabled);
    if (allFreeSlots.isEmpty) {
      -1
    } else {
      val bestPlace = allFreeSlots.minBy(distToClosestPedestrian(_));
      println(bestPlace)
      bestPlace.park(carType)
      bestPlace.index
    }    
  }
  def distToClosestPedestrian(p : ParkingSlot): Int = {
    pedestrianIndexes
      .map((ps:Int) => math.abs(ps - p.index))
      .min(Integer.compare(_, _))
  }
  def unparkCar(index: Int): Boolean = {
    val x = slots(index);
    x.unpark()
  }
  private def toGlobalIndex(rowIndex: Int, colIndex: Int) = {
		if (rowIndex % 2 == 0) {
			rowIndex*size + colIndex
		} else {
			rowIndex*size + size - 1 - colIndex
		}
  }
  
//    		  chars.foldRight("")((ps:ParkingSlot, acc:String) => acc + ps.toCharCode)
  override def toString() = {
    val rows = for (rowIndex <- 0 to size - 1) yield {
      val chars = for (colIndex <- 0 to size - 1) yield slots(toGlobalIndex(rowIndex, colIndex))
      chars.foldLeft("")(_ + _.toCharCode)
    }
    rows.reduce(_ + "\n" + _)
  }
}

trait ParkingSlot {
  def index: Int
	def isFree: Boolean
	val isDisabled: Boolean
	def park(carType: Char)
  def unpark():Boolean
  def toCharCode:Character
}
class NormalSlot(val index : Int) extends ParkingSlot {
  var carType:Option[Character] = None
  def isFree = carType.isEmpty
  val isDisabled = false
  def park(carType: Char) = this.carType = Some(carType)
  def unpark(): Boolean = {
    if (carType.isEmpty) {
      false
    } else {
      carType = None
      true
    }
  }
  def toCharCode = {
    if (carType == None) 'U' else carType.get
  }
  override def toString() = s"Normal(${index})=${carType}"
}
class PedestrianSlot(val index : Int) extends ParkingSlot {
	def isFree = false
	val isDisabled = false
	def park(carType:Char) = throw new NotImplementedError
  val unpark = false
  val toCharCode = '='
}
class DisabledSlot(val index : Int) extends ParkingSlot {
  private var free = true
  def isFree = free
  val isDisabled = true
  def park(carType: Char) = free = false
  def unpark(): Boolean = {
    if (free) {
      false
    } else {
      free = true
      true
    }
  }
  def toCharCode = {
    if (free) '@' else 'D'
  }
}
