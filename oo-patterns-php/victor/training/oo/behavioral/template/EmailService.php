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

class EmailService
{
    /** @var  EmailTemplate */
    private $emailTemplate;



    public const MAX_RETRIES = 3;

    public function __construct(EmailTemplate $emailTemplate)
    {
        $this->emailTemplate = $emailTemplate;
    }

    public function sendEmail(string $emailAddress)
    {
        $context = new EmailContext(/*smtpConfig,etc*/);
        for ($i = 0; $i < self::MAX_RETRIES; $i++) {
            $email = new Email();
            $email->setSender("noreply@corp.com");
            $email->setReplyTo("/dev/null");
            $email->setTo($emailAddress);
            $this->emailTemplate->fill($email);
            $success = $context->send($email);
            if ($success) break;
        }
    }


}
interface EmailTemplate {
    function fill(Email $email): void;
}
class OrderReceivedEmail implements EmailTemplate {
    public function fill(Email $email): void {
        $email->setSubject("Order Received");
        $email->setBody("Thank you for your order");
    }
}
class OrderShippedEmail implements EmailTemplate {
    public function fill(Email $email): void {
        $email->setSubject("Order Shipped");
        $email->setBody("Ti-am trimas.");
    }
}

$emailService = new EmailService(new OrderReceivedEmail());
$emailService->sendEmail("a@b.com");

$emailService = new EmailService(new OrderShippedEmail());
$emailService->sendEmail("a@b.com");