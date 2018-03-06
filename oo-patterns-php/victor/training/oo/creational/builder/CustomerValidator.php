<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/18/2017
 * Time: 1:46 AM
 */

namespace training\oo\creational\builder;


class CustomerValidator
{


    public function validate(Customer $customer)
    {
        if ($customer->getName() == '') {
            throw new \Exception("Missing customer name");
        }

        $this->validateAddress($customer->getAddress());
        //etc ...
    }

    private function validateAddress(Address $address)
    {
        if ($address == null) {
            throw new \Exception("Missing customer address");
        }

        if ($address->getCity() == "") {
            throw new \Exception("Missing address city");
        }
    }
}