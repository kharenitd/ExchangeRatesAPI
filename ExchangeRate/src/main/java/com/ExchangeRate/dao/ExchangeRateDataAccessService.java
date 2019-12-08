package com.ExchangeRate.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;
@Repository("ExchangeDao")
public class ExchangeRateDataAccessService implements ExchangeRateDao{
	/**
	 * This method will works as follows to find the averages of exchange rates of various currencies
	 * for a given time period.
	 * 1. Parses the given start and end date from string to Date format
	 * 2. Iterates from start date till end date for getting the exchange rates of all currencies , by
	 *   making a HTTP GET request on API "https://api.exchangeratesapi.io/" for all dates between startdate and 
	 *   end date.Simultaneously it creates a HashMap(named as totalRates) for storing the summation of exchange rates of 
	 *   all currencies of this time period.
	 * 3.Finally it returns a HashMap which contains currency name and its  average exchange rate as a key-value pair of given currencies list
	 *   for given time period
	 * @param date1  Starting  date of time span whose average is to be calculated
	 * @param date2  End  date of time span whose average is to be calculated
	 * @param currencies List  of currencies whose average needs to be calculated
	 */
	
	@Override
	public HashMap<String, Double> getAvgExchangeRate(String date1, String date2, List<String> currencies) {
		{
			HashMap<String, Double> totalRates = new HashMap<String, Double>();
			HashMap<String, Double> finalResult = null;
			List<Date> datesInRange = new ArrayList<>();
		    Calendar calendar = new GregorianCalendar();
		    Calendar endCalendar = new GregorianCalendar();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    Date startDate;
			try {
				startDate = sdf.parse(date1);
			    Date endDate= sdf.parse(date2);
			    calendar.setTime(startDate);
			    endCalendar.setTime(endDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		     
			
		    while (calendar.before(endCalendar)) {
		    	
		        Date result = calendar.getTime();
		        String currentDate=sdf.format(result);
		        String url  = "https://api.exchangeratesapi.io/"+currentDate;
				try {
					URL urlObj = new URL(url);
					HttpURLConnection con =  (HttpURLConnection)urlObj.openConnection();
					con.setRequestMethod("GET");
					int reponseCode = con.getResponseCode();
					//System.out.println("sending GETrequest to url: "+url);
					//System.out.println("getting response code:"+reponseCode);
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					
					String inputLine ;
					StringBuffer response = new StringBuffer();
					while((inputLine=in.readLine())!=null) {
						response.append(inputLine);
					}
					in.close();
					//System.out.println("response:"+response.toString());
					JSONObject myResponse = new JSONObject(response.toString());
					// use of lamda expression 
						 myResponse.keySet().forEach(keyStr ->
						    {
						    	 Object keyvalue = myResponse.get(keyStr);
						        if(keyStr.equals("rates")) {
						        	 ((JSONObject) keyvalue).keySet().forEach(keyStr2 ->
						        	    {
						        	        Object keyvalue2 = ((JSONObject) keyvalue).get(keyStr2);
						        	        //System.out.println("key: "+ keyStr2 + " value: " + keyvalue2);
						        	        if(totalRates.containsKey(keyStr2)) {
						        	        	double val = totalRates.get(keyStr2);
						        	        	val+= (double)keyvalue2;
						        	        	totalRates.put(keyStr2, val);
						        	        }
						        	        else {
						        	        	double val = 0;
						        	        	val+= (double)keyvalue2;
						        	        	totalRates.put(keyStr2, val);
						        	        }
						        	        
						        	    });
						        }
						        else { 
						       // System.out.println("key: "+ keyStr + " value: " + keyvalue);
						        }
						    });
						 
					
						 
						 
				} catch ( IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        datesInRange.add(result);
		        calendar.add(Calendar.DATE, 1);
		        
		    }
		    Double noOfDays=(double) datesInRange.size();
		    finalResult = new HashMap<String, Double>();
		    
			for(String s:currencies) {
				//System.out.println("total of "+s+" = "+totalRates.get(s)+"  No of days : "+noOfDays);
				//System.out.println("Avg of "+s+" : "+totalRates.get(s)/noOfDays);
				finalResult.put(s,(totalRates.get(s)/noOfDays));
			}
			return finalResult;
		}

	}
	
}
