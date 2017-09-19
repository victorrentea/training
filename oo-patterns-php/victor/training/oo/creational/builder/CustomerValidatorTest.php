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

    /** @test */
    public function aValidCustomer_OK() {
        $this->validator->validate($this->validCustomer);
    }

    /**
     * @test
     * @expectedException \Exception
     */
    public function aCustomerWithoutName_Fails() {
        $this->validator->validate($this->validCustomer->setName(""));
    }

    /**
     * @test
     * @expectedException \Exception
     */
    public function aCustomerAddressWithoutCity_Fails() {
        $this->validator->validate(
            $this->validCustomer
                ->setAddress($this->validCustomer->getAddress()
                    ->setCity("")
                ));
    }

}