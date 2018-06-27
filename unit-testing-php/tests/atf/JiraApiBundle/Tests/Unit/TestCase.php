<?php

namespace Emag\Core\JiraApiBundle\Tests\Unit;

use Guzzle\Http\Exception\BadResponseException;

abstract class TestCase extends \PHPUnit_Framework_TestCase
{
    /**
     * Performs initialisation at the start of each test.
     */
    protected function setUp()
    {
        parent::setUp();
    }

    /**
     * Mocks a Guzzle client with specified request.
     *
     * @param mixed $request
     *
     * @return \Guzzle\Http\Client
     */
    private function mockClientWithRequest($request)
    {
        $client = $this
            ->getMockBuilder('Guzzle\Http\Client')
            ->disableOriginalConstructor()
            ->getMock();

        $client
            ->expects($this->any())
            ->method('get')
            ->will(
                $this->returnValue($request)
            );

        return $client;
    }

    /**
     * Get a Guzzle client mock object which returns the specified
     * JSON file as an array.
     *
     * @param string $jsonFile
     *
     * @return \Guzzle\Http\Client
     *
     * @throws \RuntimeException
     */
    protected function getClientMock($jsonFile)
    {
        if (false === file_exists($jsonFile)) {
            throw new \RuntimeException('Unable to find JSON file.');
        }

        $request = $this->getMock('Guzzle\Http\Message\RequestInterface');

        $request
            ->expects($this->any())
            ->method('send')
            ->will(
                $this->returnValue(new JsonResponseMock($jsonFile))
            );

        return $this->mockClientWithRequest($request);
    }

    /**
     * Get a Guzzle client mock object which triggers a BadResponseException.
     *
     * @return \Guzzle\Http\Client
     */
    protected function getClientMockException()
    {
        $request = $this
            ->getMockBuilder('Guzzle\Http\Message\ClientInterface')
            ->setMethods(array('send'))
            ->getMock();

        $request
            ->expects($this->once())
            ->method('send')
            ->will(
                $this->throwException(new BadResponseException())
            );

        return $this->mockClientWithRequest($request);
    }

    /**
     * Get a Guzzle client mock object which returns no data.
     *
     * @return \Guzzle\Http\Client
     */
    protected function getClientMockNoData()
    {
        $request = $this->getMock('Guzzle\Http\Message\RequestInterface');

        $request
            ->expects($this->any())
            ->method('send')
            ->will(
                $this->returnValue(new EmptyResponseMock())
            );

        return $this->mockClientWithRequest($request);
    }

    /**
     * Get a Guzzle client mock object which returns an error.
     *
     * @return \Guzzle\Http\Client
     */
    protected function getClientMockErrors()
    {
        $request = $this->getMock('Guzzle\Http\Message\RequestInterface');

        $request
            ->expects($this->any())
            ->method('send')
            ->will(
                $this->returnValue(new ErrorResponseMock())
            );

        return $this->mockClientWithRequest($request);
    }

    /**
     * Performs clean-up operations after each test.
     */
    protected function tearDown()
    {
        parent::tearDown();
    }
}
