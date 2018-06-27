<?php

namespace Emag\Core\JiraApiBundle\Tests\Unit;

/**
 * Mocks JSON response that returns an error for unit testing purposes.
 */
class ErrorResponseMock
{
    public function json()
    {
        return array(
            'errorMessages' => array('This is an error message.'),
            'errors' => array(),
        );
    }

    public function getBody($asString = false)
    {
        return json_encode($this->json());
    }
}
