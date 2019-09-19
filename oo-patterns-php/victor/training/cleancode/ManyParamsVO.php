<?php

class ManyParamsVO {
   public function fun() {
        (new ManyParamsVO())->placeOrder("John", "Doe", "St. Albergue", "Paris", 99);
    }
    public function placeOrder(string $fName, string $lName, string $city, string $streetName, int $streetNumber) {

}
}

class AnotherClass {
    public function otherMethod(string $firstName, string $lastName, int $x) {
        //distant logic
    }
}