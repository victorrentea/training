<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 9:24 PM
 */

namespace training\oo\creational\builder;
include "Customer.php";
include "Address.php";
include "CustomerValidator.php";

class CustomerValidatorTest extends \PHPUnit\Framework\TestCase
{
    // SOLUTION (
    /* @var Customer */
    private $validCustomer;
    /* @var CustomerValidator*/
    private $validator;

    protected function setUp()
    {
        $this->validator = new CustomerValidator();
        $this->validCustomer = (new Customer())
            ->setName("John Doe")
            ->setAddress((new Address())
                ->setCity("Bucharest"));
    }

    /** @test
     * @throws \Exception
     */
    public function aValidCustomer_OK() {
        $this->validator->validate($this->validCustomer); // SOLUTION
        self::assertTrue(true); // SOLUTION
    }
    // SOLUTION )

    /** @test
     * @throws \Exception
     */
    public function aValidCustomer_OK1() {
        $customer = new Customer();
        $customer->setName("John Doe");
        $address = new Address();
        $address->setCity("Bucharest");
        $customer->setAddress($address);
        $validator = new CustomerValidator();
        $validator->validate($this->validCustomer);
        self::assertTrue(true);
    }



    /**
     * @test
     * @expectedException \Exception
     */
    public function aCustomerWithoutName_Fails() {
        $this->validator->validate($this->validCustomer->setName("")); // SOLUTION
    }

    /**
     * @test
     * @expectedException \Exception
     */
    public function aCustomerAddressWithoutCity_Fails() {
        // SOLUTION (
        $this->validator->validate(
            $this->validCustomer
                ->setAddress($this->validCustomer->getAddress()
                    ->setCity("")
                ));
        // SOLUTION )
    }

}