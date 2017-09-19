<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:28 AM
 */

namespace victor\training\oo\behavioral\template;

include "Email.php";
include "EmailContext.php";

abstract class EmailTemplate
{
    public const MAX_RETRIES = 3;

    public function sendEmail(string $emailAddress)
    {
        $context = new EmailContext(/*smtpConfig,etc*/);
        for ($i = 0; $i < self::MAX_RETRIES; $i++) {
            $email = new Email();
            $email->setSender("noreply@corp.com");
            $email->setReplyTo("/dev/null");
            $email->setTo($emailAddress);
            $this->fillEmail($email);
            $success = $context->send($email);
            if ($success) break;
        }
    }

    public abstract function fillEmail(Email $email): void;
}
class OrderReceivedEmail extends EmailTemplate {
    public function fillEmail(Email $email): void {
        $email->setSubject("Order Received");
        $email->setBody("Thank you for your order");
    }
}
class OrderShippedEmail extends EmailTemplate {
    public function fillEmail(Email $email): void {
        $email->setSubject("Order Shipped");
        $email->setBody("Ti-am trimas.");
    }
}

(new OrderReceivedEmail())->sendEmail("a@b.com");
(new OrderShippedEmail())->sendEmail("a@b.com");