package cleancode;

public class ManyParamsVO {
    public static void main(String[] args) {
        new ManyParamsVO().placeOrder("John", "Doe", "St. Albergue", "Paris", 99);
    }
    public void placeOrder(String fName, String lName, String city, String streetName, Integer streetNumber) {

    }
}

class AnotherClass {
    public void otherMethod(String firstName, String lastName, int x) {
        //distant logic
    }
}