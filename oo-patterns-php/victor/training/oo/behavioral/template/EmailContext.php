<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:27 AM
 */

namespace victor\training\oo\behavioral\template;


class EmailContext
{
    public function send(Email $email): bool
    {
        printf("Trying to send " . $email . "\n");
        return rand(0, 1) == 0;
    }
}