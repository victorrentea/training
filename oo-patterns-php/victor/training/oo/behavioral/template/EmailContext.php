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
        printf("Trying to send " . $email . "\n" . " with subject " . $email->getSubject());
        if (rand(0, 1) == 0) {
            printf("SUCCESS");
            return true;
        } else {
            printf("ERROR");
            return false;
        }
    }
}