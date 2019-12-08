package com.ExchangeRate.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ExchangeRate.service.ExchangeRateService;
/**
 * Receives GET request from client along with the parameters and execution starts
 * @author Himanshu
 */
@RequestMapping("api/v1/exchangeRate")
@RestController
public class ExchangeRateController {
	private ExchangeRateService exchangeRateService;
	@Autowired
	public ExchangeRateController(ExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}
	/**
	 * 
	 * @param date1 Starting  date of time span whose average is to be calculated
	 * @param date2 End  date of time span whose average is to be calculated
	 * @param currencies List  of currencies whose average needs to be calculated
	 * @return A HashMap containing currencies name and their average exchange rates for given time span
	 */
	@GetMapping
	public HashMap<String,Double> getAvgExhangeRates(@RequestParam("startDate") String date1,@RequestParam("endDate") String date2,@RequestParam("currenciesList") List<String> currencies){
		return exchangeRateService.getAvgExchangeRate2(date1,date2,currencies);
	}
}
