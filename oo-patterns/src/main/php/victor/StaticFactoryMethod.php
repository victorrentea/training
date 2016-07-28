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
	
	

	
	
	
	
	
	public function goodFunction(goodName) {
		Booking.constructWithCircuit(goodName);
	
	}
}
