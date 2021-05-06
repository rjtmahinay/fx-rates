package com.rates.model;

import lombok.Data;

/**
 * @author Tristan Mahinay
 */
@Data
public class RateInformation {

	private String baseCurrency;
	private Double baseRateAmount;
	private String targetCurrency;
	private Double targetRateAmount;

}
