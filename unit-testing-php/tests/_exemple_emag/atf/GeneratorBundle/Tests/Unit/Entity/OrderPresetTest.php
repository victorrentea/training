<?php
namespace Emag\Core\GeneratorBundle\Tests\Unit\Entity;

use Emag\Core\GeneratorBundle\Entity\OrderPreset;

class OrderPresetTest extends \PHPUnit_Framework_TestCase
{
    public function testEntityGettersAndSetters()
    {
        $orderPreset = new OrderPreset();

        $this->assertEquals($orderPreset, $orderPreset->setName('Some name'));
        $this->assertEquals($orderPreset, $orderPreset->setDescription('Some description'));
        $this->assertEquals($orderPreset, $orderPreset->setCategory('Category'));
        $this->assertEquals($orderPreset, $orderPreset->setParams(json_encode(['key' => 'val'])));

        $this->assertEquals('Some name', $orderPreset->getName());
        $this->assertEquals('Some description', $orderPreset->getDescription());
        $this->assertEquals('Category', $orderPreset->getCategory());
        $this->assertEquals(json_encode(['key' => 'val']), $orderPreset->getParams());
    }
}