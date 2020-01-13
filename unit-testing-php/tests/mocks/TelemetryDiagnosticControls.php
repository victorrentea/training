<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 01-Aug-18
 * Time: 12:38 PM
 */

namespace PhpUnitWorkshop\mocks;


class TelemetryDiagnosticControls
{
    public const DIAGNOSTIC_CHANNEL_CONNECTION_STRING = '*111#';

    private $telemetryClient;
    private $diagnosticInfo = "";

    public function __construct(TelemetryClient $telemetryClient)
    {
        $this->telemetryClient = $telemetryClient;
    }

    public function getDiagnosticInfo(): String
    {
        return $this->diagnosticInfo;
    }

    public function setDiagnosticInfo(String $diagnosticInfo): void
    {
        $this->diagnosticInfo = $diagnosticInfo;
    }

    public function checkTransmission()
    {
        $this->telemetryClient->disconnect();

        $currentRetry = 1;
        while (!$this->telemetryClient->getOnlineStatus() && $currentRetry <= 3) {
            $this->telemetryClient->connect(self::DIAGNOSTIC_CHANNEL_CONNECTION_STRING);
            $currentRetry++;
        }


        if (!$this->telemetryClient->getOnlineStatus()) {
            throw new \Exception("Unable to connect.");
        }

        $this->telemetryClient->configure($this->createConfiguration());

        $this->telemetryClient->send(TelemetryClient::DIAGNOSTIC_MESSAGE);
        $this->diagnosticInfo = $this->telemetryClient->receive();
    }

    public function createConfiguration(): TelemetryClientConfiguration
    {
        $config = new TelemetryClientConfiguration();
        $config->setSessionId(uniqid());
        $config->setSessionStart(time());
        $config->setAckMode(TelemetryClientConfiguration::ACK_NORMAL);
        return $config;
    }


}
class User {
    private $active = false;

    /**
     * @return bool
     */
    public function isActive(): bool
    {
        return $this->active;
    }

    /**
     * @param bool $active
     */
    public function setActive(bool $active): void
    {
        $this->active = $active;
    }

}