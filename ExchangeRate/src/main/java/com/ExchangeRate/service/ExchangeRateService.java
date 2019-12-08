package com.ExchangeRate.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ExchangeRate.dao.ExchangeRateDao;
@Service
public class ExchangeRateService {
	private ExchangeRateDao exchangeRateDao;
	@Autowired
	public ExchangeRateService(@Qualifier("ExchangeDao") ExchangeRateDao exchangeRateDao) {
		this.exchangeRateDao =exchangeRateDao;
	}
	
	public HashMap<String, Double> getAvgExchangeRate2(String date1, String date2, List<String> currencies){
		return exchangeRateDao.getAvgExchangeRate(date1, date2, currencies);
		
	}

}
