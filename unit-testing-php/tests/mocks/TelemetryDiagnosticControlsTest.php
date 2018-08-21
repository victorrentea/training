<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 01-Aug-18
 * Time: 12:58 PM
 */

namespace PhpUnitWorkshop\mocks;


use PHPUnit\Framework\MockObject\MockObject;
use PHPUnit\Framework\TestCase;

class TelemetryDiagnosticControlsTest extends TestCase
{
    /** @var TelemetryClient|MockObject */
    private $telemetryClient;
    /** @var TelemetryDiagnosticControls */
    private $subject;

    public function setUp()
    {
        $this->telemetryClient = $this->createMock(
            TelemetryClient::class
        );
        $this->subject = new TelemetryDiagnosticControls($this->telemetryClient);
    }

    public function testCheckTransmissionThrowsWhenGetOnlineStatusReturnsFalse()
    {
        $this->telemetryClient->method('getOnlineStatus')
            ->willReturn(false);
        $this->expectException(\Exception::class);

        $this->subject->checkTransmission();
    }
    public function testCheckTransmissionCallsDisconnect()
    {
        $this->telemetryClient->method('getOnlineStatus')
            ->willReturn(true);
        $this->telemetryClient->expects($this->once())
            ->method('disconnect');

        $this->subject->checkTransmission();
    }

    public function testCheckTransmissionCallsSend()
    {
        $this->telemetryClient->method('getOnlineStatus')
            ->willReturn(true); // stabuiesc metoda
        // STUB: o invat sa zica "To be or not to be".
        $this->telemetryClient->expects($this->exactly(1))
            ->method('send')
            ->with(TelemetryClient::DIAGNOSTIC_MESSAGE)
        ;
        // MOCK: verific ca o metoda este invocata in cele ce urmeaza in acest test.

        $this->subject->checkTransmission();
    }

    public function testCheckTransmissionCallsReceive()
    {
        $this->telemetryClient->method('getOnlineStatus')
            ->willReturn(true);
        $this->telemetryClient->method('receive')
            ->willReturn('mamaie');

        $this->subject->checkTransmission();

        $this->assertEquals('mamaie', $this->subject->getDiagnosticInfo());
    }

    /** @var TelemetryClientConfiguration  */
    private $config2;

    public function testCheckTransmissionCallsConfigure()
    {
        $config = $this->subject->createConfiguration();

        $this->assertEquals(TelemetryClientConfiguration::ACK_NORMAL, $config->getAckMode());
    }

}