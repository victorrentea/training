<?php
/**
 * .-------------------------------------------------------------------.
 * | IMPORTANT!                                                        |
 * | Replace the APP code in the namespace declaration below           |
 * | with you app code that you used for the --namespace option        |
 * | when bootstrapping your codeception repository for imported tests |
 * '-------------------------------------------------------------------'
 */
namespace APP\Helper;

use Codeception;

/**
 * In order to use this helper please enable it in you suite config
 * eg. acceptance.suite.yml:
 * class_name: AcceptanceTester
 * modules:
 *   enabled:
 *     - APP\Helper\Atf
 */
class Atf extends \Codeception\Module
{
    /**
     * @param $param
     * @return mixed
     * @throws Codeception\Exception\ConfigurationException
     */
    public function params($param)
    {
        $config = Codeception\Configuration::config();

        if (!array_key_exists('modules', $config)
            || !array_key_exists('config', $config['modules'])
            || !array_key_exists('Params', $config['modules']['config'])
        ) {
            return [];
        }

        $params = $config['modules']['config']['Params'];

        if (!array_key_exists($param, $params)) {
            throw new Codeception\Exception\ConfigurationException(
                sprintf('Parameter %s not found in configuration', $param)
            );
        }

        return $params[$param];
    }

    public function getDistributionUrl($distribution)
    {
        $stackId = $this->params('STACK_ID');

        $distributions = [
            1 => [
                "scm" => "http://scm.emag.local",
                "atf" => "http://atf.emag.local",
            ],
            2 => [
                "scm" => "http://scm.emag.local2",
                "atf" => "http://atf.emag.local2",
            ],
        ];

        return $distributions[$stackId][$distribution];
    }
}
