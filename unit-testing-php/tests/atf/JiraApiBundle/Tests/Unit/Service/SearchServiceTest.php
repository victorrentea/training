<?php
namespace Emag\Core\JiraApiBundle\Tests\Unit\Service;

use Emag\Core\JiraApiBundle\Tests\Unit\TestCase;
use Emag\Core\JiraApiBundle\Service\SearchService;

/**
 * @group unit-tests
 */
class SearchServiceTest extends TestCase
{
    public function testSearchServiceSearch()
    {
        $jsonFile = __DIR__ . '/../assets/response/search.json';

        $service = new SearchService(
            $this->getClientMock($jsonFile)
        );

        $params = array(
            'jql' => 'id=AA-999'
        );

        $service->setStart(0);
        $service->setLimit(50);

        $result = $service->search($params);

        $this->assertEquals(5, count($result));
        $this->assertEquals($service->getStart(), 0);
        $this->assertEquals($service->getLimit(), 50);
    }

    /**
     * @expectedException \Guzzle\Http\Exception\BadResponseException
     */
    public function testSearchServiceSearchException()
    {
        $service = new SearchService($this->getClientMockException());

        $service->search(array());
    }

    public function testSearchServiceSearchNoData()
    {
        $service = new SearchService($this->getClientMockNoData());

        $result = $service->search(array());

        $this->assertEquals(array(), $result);
    }

    public function testSearchServiceSearchErrors()
    {
        $service = new SearchService($this->getClientMockErrors());

        $result = $service->search(array());

        $this->assertEquals(false, $result);
    }
}
