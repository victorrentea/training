package victor.kata.parking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import victor.kata.parking.Parking;
import victor.kata.parking.ParkingBuilder;

public class ParkingTest {
    private static final int FIRSTUPEDESTRIANUEXITUINDEX = 8;
    private Parking parking;

    @Before
    public void setUp() {
        parking = new ParkingBuilder()
        		.withSquareSize(5)
        		.withPedestrianExit(FIRSTUPEDESTRIANUEXITUINDEX)
        		.withPedestrianExit(12)
        		.withDisabledBay(5)
        		.withDisabledBay(10)
        		.build();
    }

    @Test
    public void testGetAvailableBays() {
        assertEquals(23, parking.getAvailableBays());
    }

    @Test
    public void testParkCarVehiculeTypeC() {
        assertEquals(7, parking.parkCar('C'));
        assertEquals(22, parking.getAvailableBays());
    }

    @Test
    public void testParkCarVehiculeTypeM() {
        assertEquals(7, parking.parkCar('M'));
        assertEquals(22, parking.getAvailableBays());
    }

    @Test
    public void testParkCarTwoVehicules() {
        assertEquals(7, parking.parkCar('C'));
        assertEquals(22, parking.getAvailableBays());

        assertEquals(9, parking.parkCar('M'));
        assertEquals(21, parking.getAvailableBays());
    }

    @Test
    public void testParkCarDisabled() {
        assertEquals(10, parking.parkCar('D'));
        assertEquals(22, parking.getAvailableBays());

        assertEquals(5, parking.parkCar('D'));
        assertEquals(21, parking.getAvailableBays());

        assertEquals(-1, parking.parkCar('D'));
        assertEquals(21, parking.getAvailableBays());
    }

    @Test
    public void testUnparkCar() {
        final int firstCarBayIndex = parking.parkCar('C');
        assertTrue(parking.unparkCar(firstCarBayIndex));
        assertEquals(23, parking.getAvailableBays());
        assertFalse(parking.unparkCar(firstCarBayIndex));

        final int secondCarBayIndex = parking.parkCar('D');
        assertTrue(parking.unparkCar(secondCarBayIndex));
        assertEquals(23, parking.getAvailableBays());
        assertFalse(parking.unparkCar(secondCarBayIndex));

        assertFalse(parking.unparkCar(FIRSTUPEDESTRIANUEXITUINDEX));
    }

    @Test
    public void testToString() {
        assertEquals("UUUUU\nU=UU@\n@U=UU\nUUUUU\nUUUUU", parking.toString());
    }

    @Test
    public void testCompleteSolution() {
        assertEquals(7, parking.parkCar('C'));
        assertEquals("UUUUU\nU=CU@\n@U=UU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(22, parking.getAvailableBays());

        assertEquals(9, parking.parkCar('C'));
        assertEquals("UUUUU\nC=CU@\n@U=UU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(21, parking.getAvailableBays());

        assertEquals(11, parking.parkCar('M'));
        assertEquals("UUUUU\nC=CU@\n@M=UU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(20, parking.getAvailableBays());

        assertEquals(13, parking.parkCar('M'));
        assertEquals("UUUUU\nC=CU@\n@M=MU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(19, parking.getAvailableBays());

        assertEquals(10, parking.parkCar('D'));
        assertEquals("UUUUU\nC=CU@\nDM=MU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(18, parking.getAvailableBays());

        assertEquals(5, parking.parkCar('D'));
        assertEquals("UUUUU\nC=CUD\nDM=MU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(17, parking.getAvailableBays());

        assertEquals(-1, parking.parkCar('D'));
        assertEquals("UUUUU\nC=CUD\nDM=MU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(17, parking.getAvailableBays());

        assertFalse(parking.unparkCar(3));
        assertEquals("UUUUU\nC=CUD\nDM=MU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(17, parking.getAvailableBays());

        assertTrue(parking.unparkCar(13));
        assertEquals("UUUUU\nC=CUD\nDM=UU\nUUUUU\nUUUUU", parking.toString());
        assertEquals(18, parking.getAvailableBays());
    }
}
