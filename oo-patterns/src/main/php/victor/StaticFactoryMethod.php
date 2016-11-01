<?php

namespace MS\ExamplePHP\ProxyPattern\Service;

use MS\ExamplePHP\ObserverPattern\Service\OrderService as DefaultOrderService;
use MS\ExamplePHP\ObserverPattern\Service\OrderServiceInterface;
use Psr\Log\LoggerInterface;


class Booking 
{
    /** @var  Flight */
    private $flight;
	
	 /** @var  MemberCard */
    private $memberCard;
	
	private function __construct() {
		
	}
	
	/**
     * @param Flight $flight
     */
	public static function constructWithFlight($flight) {
		return new Booking()->setFlight($flight);
	}
	
	/**
     * @param Flight $flight
	 * @param MemberCard $memberCard
     */
	public static function constructWithFlightAndMemberCard($flight, $memberCard) {
		return new Booking()->setFlight($flight)->;
	}
	
	/**
     * @param Flight $flight
	 * @param MemberCard $memberCard
     */
	public static function constructWithCircuit($circuit) {
		
	}
	
	
	public function myMethod() {
		args...
	}
	public function MyMethodWith1Param($p1) {
		
	}
	
	public function MyMethodWith2Param($p1, $p2) {
		
	}
	
	
	
	
	
	
	
	public function goodFunction(goodName) {
		Booking.constructWithCircuit(goodName);
	
	}
}
