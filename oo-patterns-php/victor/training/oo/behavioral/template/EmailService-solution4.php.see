<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:28 AM
 */

namespace victor\training\oo\behavioral\template;

use phpDocumentor\Reflection\Types\Callable_;

include "Email.php";
include "EmailContext.php";

class EmailService
{
    public const MAX_RETRIES = 3;

    public function sendEmail(string $emailAddress, callable $emailTemplate )
    {
        $context = new EmailContext(/*smtpConfig,etc*/);
        for ($i = 0; $i < self::MAX_RETRIES; $i++) {
            $email = new Email();
            $email->setSender("noreply@corp.com");
            $email->setReplyTo("/dev/null");
            $email->setTo($emailAddress);
            $emailTemplate($email);
            $success = $context->send($email);
            if ($success) break;
        }
    }
}

class EmailTemplates {
    static function orderReceivedEmail(Email $email) {
        $email->setSubject("Order Received");
        $email->setBody("Thank you for your order");
    }
    static function orderShippedEmail(Email $email) {
        $email->setSubject("Order Shipped");
        $email->setBody("Ti-am trimas.");
    }
}

$emailService = new EmailService();
$emailService->sendEmail("a@b.com", [EmailTemplates::class, "orderReceivedEmail"]);
$emailService->sendEmail("a@b.com", [EmailTemplates::class, "orderShippedEmail"]);


$emailService->sendEmail("a@b.com", function($email) { EmailTemplates::orderReceivedEmail($email);});
$emailService->sendEmail("a@b.com", function($email) { EmailTemplates::orderShippedEmail($email);});