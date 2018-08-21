<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 01-Aug-18
 * Time: 12:39 PM
 */

namespace PhpUnitWorkshop\mocks;


class TelemetryClient
{
    const DIAGNOSTIC_MESSAGE = "AT#UD";

    private $onlineStatus;
    private $diagnosticMessageResult = "";

    private $connectionEventsSimulator;

    public function __construct()
    {
        $this->connectionEventsSimulator = new Random(42);
    }

    public function getOnlineStatus():bool {
        return $this->onlineStatus;
    }

public function connect(String $telemetryServerConnectionString):void {
    if ($telemetryServerConnectionString == null || "" == $telemetryServerConnectionString) {
        throw new \Exception();
    }

    // simulate the operation on a real modem
    $success = $this->connectionEventsSimulator->nextInt(10) <= 8;

	$this->onlineStatus = $success;
	}

	public function disconnect() {
        $this->onlineStatus = false;
	}

	public function send(String $message) {
        if ($message == null || "" ==$message) {
            throw new \Exception();
        }

    if ($message == self::DIAGNOSTIC_MESSAGE) {
        // simulate a status report
        $this->diagnosticMessageResult = "LAST TX rate................ 100 MBPS\r\n"
            . "HIGHEST TX rate............. 100 MBPS\r\n" . "LAST RX rate................ 100 MBPS\r\n"
            . "HIGHEST RX rate............. 100 MBPS\r\n" . "BIT RATE.................... 100000000\r\n"
            . "WORD LEN.................... 16\r\n" . "WORD/FRAME.................. 511\r\n"
            . "BITS/FRAME.................. 8192\r\n" . "MODULATION TYPE............. PCM/FM\r\n"
            . "TX Digital Los.............. 0.75\r\n" . "RX Digital Los.............. 0.10\r\n"
            . "BEP Test.................... -5\r\n" . "Local Rtrn Count............ 00\r\n"
            . "Remote Rtrn Count........... 00";

        return;
    }

    // here should go the real Send operation (not needed for this exercise)
}

	public function receive() {
        $message = "";

		if ($this->diagnosticMessageResult == null || "" == $this->diagnosticMessageResult) {
            // simulate a received message (just for illustration - not needed for this exercise)
            $message = "";
            $messageLength = $this->connectionEventsSimulator->nextInt(50) + 60;
			for ($i = $messageLength; $i >= 0; --$i) {
                $message += $this->connectionEventsSimulator->nextInt(40) + 86;
			}

		} else {
            $message = $this->diagnosticMessageResult;
            $this->diagnosticMessageResult = "";
        }

		return $message;
	}

	public function configure(TelemetryClientConfiguration $config) {
        //TODO Configure the client
    }

}