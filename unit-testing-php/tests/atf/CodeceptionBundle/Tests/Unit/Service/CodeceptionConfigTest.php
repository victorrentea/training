<?php
namespace Emag\Core\CodeceptionBundle\Tests\Unit\Service;

use Emag\Core\CodeceptionBundle\Service\CodeceptionConfig;

class CodeceptionConfigTest extends \PHPUnit_Framework_TestCase
{
    /**
     * @var array
     */
    private $data = [
        'namespace' => 'SCM',
        'actor' => 'Tester',
        'paths' => [
            'tests' => 'tests',
            'log' => 'tests/_output',
            'data' => 'tests/_data',
            'support' => 'tests/_support',
            'envs' => 'tests/_envs'
        ],
        'settings' => [
            'bootstrap' => '_bootstrap.php',
            'colors' => false,
            'memory_limit' => '1024M'
        ],
        'extensions' => [
            'enabled' => ['Codeception\Extension\RunFailed', 'Codeception\Extension\Recorder'],
            'config' => [
                'Codeception\Extension\Recorder' => [
                    'delete_successful' => true,
                    'module' => 'WebDriver'
                ]
            ]
        ],
        'modules' => [
            'config' => [
                'Db' => [
                    'dsn' => "'mysql' =>host=i3.sql8-test.stack.emag.local;dbname=emag_scm_dante'",
                    'user' => 'ciprian.nitu',
                    'password' => 'parolaciprian',
                    'dump' => 'tests/_data/dump.sql',
                    'populate' => 'true',
                    'cleanup' => 'false',
                    'reconnect' => 'true'
                ],
                'Params' => [
                    'STACK_ID' => '4',
                    'SCM\Page\Login' => [
                        'username' => 'test.programareD',
                        'password' => 'Programare2015?'
                    ]
                ]
            ]
        ]
    ];

    /**
     * @param $data
     * @param $expectedData
     * @dataProvider incompleteDataProvider
     */
    public function testDisableExtensionWillWorkOnIncompleteData($data, $expectedData)
    {
        $config = new CodeceptionConfig($data);
        $config->disableExtension('Codeception\Extension\Recorder');

        $this->assertEquals($expectedData, $config->toArray());
    }

    public function incompleteDataProvider()
    {
        return [
            [[], ['extensions' => ['enabled' => [], 'config' => []], 'modules' => ['config' => []]]],
            [['extensions' => []], ['extensions' => ['enabled' => [], 'config' => []], 'modules' => ['config' => []]]],
            [['extensions' => ['enabled' => []]], ['extensions' => ['enabled' => [], 'config' => []], 'modules' => ['config' => []]]],
            [['extensions' => ['enabled' => [], 'config' => []]], ['extensions' => ['enabled' => [], 'config' => []], 'modules' => ['config' => []]]],
        ];
    }

    public function testDisableExtensionWillDisableAnEnabledExtensionAndRemoveItsConfigData()
    {
        $data = [
            'extensions' => [
                'enabled' => [
                    'Some\Other',
                    'Codeception\Extension\Recorder',
                    'Codeception\Extension\Whatever',
                ],
                'config' => [
                    'Codeception\Extension\Recorder' => ['delete_successful' => true],
                    'Some\Other' => ['whatever' => 'dude']
                ]
            ]
        ];
        $expectedData = [
            'extensions' => [
                'enabled' => [
                    'Some\Other',
                    'Codeception\Extension\Whatever'
                ],
                'config' => [
                    'Some\Other' => ['whatever' => 'dude']
                ]
            ]
        ];

        $config = new CodeceptionConfig($data);
        $config->disableExtension('Codeception\Extension\Recorder');

        $this->assertEquals($expectedData['extensions'], $config['extensions']);
    }

    public function testPrefixPathsWillPrefixAllCodeceptionPathsAndTheDbModuleDumpPath()
    {
        $expectedConfig = $this->data;

        $config = new CodeceptionConfig($this->data);

        $config->prefixPaths('../../');

        $expectedConfig['paths']['tests'] = '../../' . $expectedConfig['paths']['tests'];
        $expectedConfig['paths']['log'] = '../../' . $expectedConfig['paths']['log'];
        $expectedConfig['paths']['data'] = '../../' . $expectedConfig['paths']['data'];
        $expectedConfig['paths']['support'] = '../../' . $expectedConfig['paths']['support'];
        $expectedConfig['paths']['envs'] = '../../' . $expectedConfig['paths']['envs'];
        $expectedConfig['modules']['config']['Db']['dump'] = '../../' . $expectedConfig['modules']['config']['Db']['dump'];

        $this->assertEquals($expectedConfig, $config->toArray());
    }

    public function testPrefixPathsWillPrefixPathsOnlyIfNotAlreadyPrefixed()
    {
        $config = $this->data;
        $config['paths']['log'] = '../../' . $config['paths']['log'];
        $config['paths']['data'] = '../../' . $config['paths']['data'];
        $config['paths']['support'] = '../../' . $config['paths']['support'];
        $expectedConfig = $config;

        $config = new CodeceptionConfig($config);

        $config->prefixPaths('../../');

        $expectedConfig['paths']['tests'] = '../../' . $expectedConfig['paths']['tests'];
        $expectedConfig['paths']['envs'] = '../../' . $expectedConfig['paths']['envs'];
        $expectedConfig['modules']['config']['Db']['dump'] = '../../' . $expectedConfig['modules']['config']['Db']['dump'];

        $this->assertEquals($expectedConfig, $config->toArray());
    }

    public function testAddRunUniquenessWillAddUniquenessToTheRunLogsAndReportsAndScreenshotAndOtherOutput()
    {
        $expectedConfig = $this->data;

        $config = new CodeceptionConfig($this->data);

        $config->addRunUniqueness($uniqueness = time());
        $expectedConfig['paths']['log'] .= "/$uniqueness";

        $this->assertEquals($expectedConfig, $config->toArray());
    }

    public function testEnableExtensionsWillEnableOnlyNewExtensions()
    {
        $config = new CodeceptionConfig(['extensions' => ['enabled' => ['First', 'Second']]]);
        $template = new CodeceptionConfig(['extensions' => ['enabled' => ['Second', 'Turd']]]);

        $config->enableExtensions($template);
        $configData = $config->toArray();

        $this->assertEquals(['First', 'Second', 'Turd'], $configData['extensions']['enabled']);
    }

    public function testConfigExtension()
    {
        $config = new CodeceptionConfig(
            ['extensions' => ['enabled' => ['First', 'Second'], 'config' => ['Second' => []]]]
        );

        $config->configExtension('Turd', ['k1' => 'v1', 'k2' => 'v2']);
        $configData = $config->toArray();

        $this->assertEquals(['Second' => [], 'Turd' => ['k1' => 'v1', 'k2' => 'v2']], $configData['extensions']['config']);
    }

    public function testConfigExtensionsWillConfigOnlyNewExtensionsFoundInTheTemplate()
    {
        $config = new CodeceptionConfig(
            ['extensions' => ['enabled' => ['First', 'Second'], 'config' => ['Second' => ['truth' => false]]]]
        );
        $template = new CodeceptionConfig(
            [
                'extensions' => [
                    'enabled' => ['First', 'Second', 'Turd'],
                    'config' => ['Second' => ['truth' => true], 'Turd' => ['black' => 'as night']]
                ]
            ]
        );

        $config->configExtensions($template);
        $configData = $config->toArray();

        $expectedConfig = ['Second' => ['truth' => false], 'Turd' => ['black' => 'as night']];
        $this->assertEquals($expectedConfig, $configData['extensions']['config']);
    }

    public function testEnableExtensionsWillAddMissingKeysToConfigArrayIfNeeded()
    {
        $config1 = new CodeceptionConfig([]);
        $config2 = new CodeceptionConfig(['extensions' => []]);
        $template = new CodeceptionConfig(['extensions' => ['enabled' => []]]);

        $config1->enableExtensions($template);
        $config2->enableExtensions($template);

        $config1Data = $config1->toArray();
        $this->assertArrayHasKey('extensions', $config1Data);
        $this->assertArrayHasKey('enabled', $config1Data['extensions']);

        $config2Data = $config2->toArray();
        $this->assertArrayHasKey('extensions', $config2Data);
        $this->assertArrayHasKey('enabled', $config2Data['extensions']);
    }

    public function testEnableExtensionsWillWorkWithMalformedTemplate()
    {
        $config = new CodeceptionConfig([]);
        $template = new CodeceptionConfig([]);

        $config->enableExtensions($template);

        $configData = $config->toArray();
        $this->assertArrayHasKey('extensions', $configData);
        $this->assertArrayHasKey('enabled', $configData['extensions']);
    }

    public function testInjectParamsWillAddParamsDataUnderTheModulesConfigParamsKeys()
    {
        $config = new CodeceptionConfig([]);
        $data = $config->toArray();

        $params = ['one' => ['little' => 'indian'], 'two' => ['little indian']];
        $config->injectParams($params);

        $data[CodeceptionConfig::MODULES][CodeceptionConfig::CONFIG][CodeceptionConfig::PARAMS] = $params;
        $this->assertEquals($data, $config->toArray());
    }

    public function testInjectParamsWillNotOverwriteParamsDataWhenTheParamsMethodArgumentIsNull()
    {
        $config = new CodeceptionConfig($this->data);

        $config->injectParams(null);

        $this->assertEquals($this->data, $config->toArray());
    }

    public function testInjectStackIdWillOverwriteStackIdWhenParamsAreDefinedInConfig()
    {
        $configData = $this->data;
        $config = new CodeceptionConfig($configData);

        $stackId = 123;
        $config->injectStackId($stackId);
        $configData['modules']['config']['Params']['STACK_ID'] = $stackId;

        $this->assertEquals($configData, $config->toArray());
    }

    public function testInjectStackIdWillDoNothingWhenParamsAreNotDefinedInConfig()
    {
        $configData = $this->data;
        unset($configData['modules']['config']['Params']);
        $config = new CodeceptionConfig($configData);

        $stackId = 123;
        $config->injectStackId($stackId);

        $this->assertEquals($configData, $config->toArray());
    }
}
