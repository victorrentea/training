<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 01-Aug-18
 * Time: 12:44 PM
 */

namespace PhpUnitWorkshop\mocks;


class TelemetryClientConfiguration
{
    const ACK_NORMAL = 1;
    const ACK_TIMEBOXED = 2;
    const ACK_FLOOD = 3;

    /** @var string */
    private $sessionId;
    /** @var int */
    private $sessionStart;
    /** @var int */
    private $ackMode;

    public function getSessionId(): string
    {
        return $this->sessionId;
    }

    public function setSessionId(string $sessionId): TelemetryClientConfiguration
    {
        $this->sessionId = $sessionId;
        return $this;
    }

    public function getSessionStart(): int
    {
        return $this->sessionStart;
    }

    public function setSessionStart(int $sessionStart): TelemetryClientConfiguration
    {
        $this->sessionStart = $sessionStart;
        return $this;
    }

    public function getAckMode(): int
    {
        return $this->ackMode;
    }

    public function setAckMode(int $ackMode): TelemetryClientConfiguration
    {
        $this->ackMode = $ackMode;
        return $this;
    }


}