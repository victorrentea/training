package com.nespresso.exercise.ticketing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TicketingTest {

    /**
     * Vending machines sell local tickets for central zone at fixed rate of 1€
     */
    @Test
    public void localTicket() {
        VendingMachine vendingMachine = new VendingMachine();
        Ticket ticket = vendingMachine.sell("Local ticket");
        assertEquals("1€", ticket.price);
    }

    /**
     * Vending machines sell local metropolitan tickets for a price depending on the zones included.
     * depending on the zones included, the price is different.
     * The price of the metropolitan ticket includes the local ticket price + a zone extra charge
     */
    @Test
    public void metropolitanZoneTicket() {
        VendingMachine vendingMachine = new VendingMachine();
        Ticket ticketZone1 = vendingMachine.sell("Metropolitan Zone1");
        assertEquals("3€", ticketZone1.price);

        Ticket ticketZone2 = vendingMachine.sell("Metropolitan Zone2");
        assertEquals("3.5€", ticketZone2.price);

        Ticket ticketZone1and2 = vendingMachine.sell("Metropolitan Zone1 Zone2");
        assertEquals("5.5€", ticketZone1and2.price);
    }

    /**
     * Vending machines can sell airport transfer tickets at fixed rates also.
     * The price for a ticket to the airport is the same regardless the terminal.
     */
    @Test
    public void airportTransferTicket() {
        VendingMachine vendingMachine = new VendingMachine();
        Ticket ticketGatwick = vendingMachine.sell("LONDON CENTRAL TO: GATWICK AIRPORT");
        assertEquals("10€", ticketGatwick.price);
        Ticket ticketLuton = vendingMachine.sell("LONDON CENTRAL TO: LUTON AIRPORT");
        assertEquals("12€", ticketLuton.price);
        Ticket ticketLutonT1 = vendingMachine.sell("LONDON CENTRAL TO: LUTON TERMINAL 1");
        assertEquals("12€", ticketLutonT1.price);
        assertEquals("LONDON CENTRAL TO: LUTON AIRPORT Ticket", ticketLutonT1.print());
        Ticket ticketLutonT2 = vendingMachine.sell("LONDON CENTRAL TO: LUTON TERMINAL 2");
        assertEquals("12€", ticketLutonT2.price);
    }

    /**
     * Vending machines can sell tickets and prices are influenced on classes (business class is more expensive than regular class).
     */
    @Test
    public void airportTransferBusinessTicket() {
        VendingMachine vendingMachine = new VendingMachine();
        Ticket ticket = vendingMachine.sell("LONDON CENTRAL TO: LUTON AIRPORT; CLASS: BUSINESS");
        assertEquals("15€", ticket.price);
        assertEquals("LONDON CENTRAL TO: LUTON AIRPORT *BUSINESS* Ticket", ticket.print());
    }

    /**
     * Vending machines can sell tickets (with different prices)
     * in the requested currency using a preset price in that currency (e.g. London to Luton is 12€ or 10£)
     * or if no preset price is defined then using internal exchange rate(1€=1.1$ 1€=0.9£).
     * The default currency is EUR
     */
    @Test
    public void differentCurrencyTicket() {
        VendingMachine vendingMachine = new VendingMachine();
        Ticket ticketLuton = vendingMachine.sell("LONDON CENTRAL TO: LUTON TERMINAL 1");
        assertEquals("12€", ticketLuton.price);
        Ticket ticketLutonGBP = vendingMachine.sell("LONDON CENTRAL TO: LUTON TERMINAL 1; CURRENCY:GBP");
        assertEquals("10£", ticketLutonGBP.price);

        Ticket ticketGatwick = vendingMachine.sell("LONDON CENTRAL TO: GATWICK AIRPORT");
        assertEquals("10€", ticketGatwick.price);
        Ticket ticketGatwickUSD = vendingMachine.sell("LONDON CENTRAL TO: GATWICK AIRPORT; CURRENCY: USD");
        assertEquals("11$", ticketGatwickUSD.price);
    }

    /**
     * Vending machines can sell tickets (with different prices) for different passenger type (adult, child, infant, disabled, etc.)
     * Child price is 50% from adult ticket, infant is free, disabled is fixed price(2€).
     */
    @Test
    public void multiplePassengersTicket() {
        VendingMachine vendingMachine = new VendingMachine();
        Ticket ticketLutonGroup1 = vendingMachine.sell("LONDON CENTRAL TO: LUTON TERMINAL 1; PASSENGERS: 1ADT, 1CHD; CURRENCY: USD");
        assertEquals("16.5$", ticketLutonGroup1.price);
        Ticket ticketLutonGroup2 = vendingMachine.sell("LONDON CENTRAL TO: LUTON TERMINAL 1; PASSENGERS: 2ADT, 1CHD, 1INF, 1DIS; CURRENCY: USD");
        assertEquals("29.7$", ticketLutonGroup2.price);
        assertEquals("LONDON CENTRAL TO: LUTON AIRPORT Ticket, 2 Adults, 1 Child, 1 Infant, 1 Disabled", ticketLutonGroup2.print());
    }
}
