<?php

namespace Emag\Core\JiraApiBundle\Tests\Unit;

/**
 * Mocks empty JSON response for unit testing purposes.
 */
class EmptyResponseMock
{

    public function json()
    {
        return array();
    }

    public function getBody($asString = false)
    {
        return "";
    }
}
