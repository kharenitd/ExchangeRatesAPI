package com.ExchangeRate.dao;

import java.util.HashMap;
import java.util.List;

public interface ExchangeRateDao {
	HashMap<String, Double> getAvgExchangeRate(String date1, String date2, List<String> currencies);
}
