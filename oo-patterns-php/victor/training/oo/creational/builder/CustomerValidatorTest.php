<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 9:24 PM
 */

namespace victor\training\oo\creational\builder;
include "Customer.php";
include "Address.php";
include "CustomerValidator.php";

class CustomerValidatorTest extends \PHPUnit_Framework_TestCase
{
    /** @test */
    public function aValidCustomer_OK() {
        $validator = new CustomerValidator();
        $customer = new Customer();
        $customer->setName("John Doe");
        $address = new Address();
        $address->setCity("Bucharest");
        $customer->setAddress($address);

        $validator->validate($customer);
    }

    /**
     * @test
     * @expectedException \Exception
     */
    public function aCustomerWithoutName_Fails() {
        $validator = new CustomerValidator();
        $customer = new Customer();
        $address = new Address();
        $address->setCity("Bucharest");
        $customer->setAddress($address);

        $validator->validate($customer);
    }

}