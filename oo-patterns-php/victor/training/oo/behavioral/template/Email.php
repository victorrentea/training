<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:15 AM
 */

namespace victor\training\oo\behavioral\template;


class Email
{
    /* @var string*/
    private $sender;
    /* @var string*/
    private $body;
    /* @var string*/
    private $replyTo;
    /* @var string*/
    private $to;
    /* @var string*/
    private $subject;

    /**
     * @return mixed
     */
    public function getSender()
    {
        return $this->sender;
    }

    /**
     * @param mixed $sender
     * @return Email
     */
    public function setSender($sender)
    {
        $this->sender = $sender;
        return $this;
    }

    /**
     * @return string
     */
    public function getBody(): string
    {
        return $this->body;
    }

    /**
     * @param string $body
     * @return Email
     */
    public function setBody(string $body): Email
    {
        $this->body = $body;
        return $this;
    }

    /**
     * @return string
     */
    public function getReplyTo(): string
    {
        return $this->replyTo;
    }

    /**
     * @param string $replyTo
     * @return Email
     */
    public function setReplyTo(string $replyTo): Email
    {
        $this->replyTo = $replyTo;
        return $this;
    }

    /**
     * @return string
     */
    public function getTo(): string
    {
        return $this->to;
    }

    /**
     * @param string $to
     * @return Email
     */
    public function setTo(string $to): Email
    {
        $this->to = $to;
        return $this;
    }

    /**
     * @return string
     */
    public function getSubject(): string
    {
        return $this->subject;
    }

    /**
     * @param string $subject
     * @return Email
     */
    public function setSubject(string $subject): Email
    {
        $this->subject = $subject;
        return $this;
    }


    public function __toString()
    {
        return "Email " . $this->subject;
    }
}