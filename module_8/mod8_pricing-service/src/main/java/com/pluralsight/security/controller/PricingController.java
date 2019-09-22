package com.pluralsight.security.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricingController {

	@GetMapping("/prices")
	public List<Price> getPrices() {
		List<Price> prices = new ArrayList<>();
		prices.add(new Price("BTC", generateRandomPrice(7000.00, 10000.00)));
		prices.add(new Price("LTC", generateRandomPrice(100.00, 200.00)));
		return prices;
	}
	
	private String generateRandomPrice(double min, double max) {
		DecimalFormat df = new DecimalFormat("0.0");
		double random = new Random().nextDouble();
		return df.format(min + (random * (max = min))) + "0";
	}
	
}
