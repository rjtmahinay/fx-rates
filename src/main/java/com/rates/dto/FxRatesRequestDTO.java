package com.rates.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Tristan Mahinay
 */
@Data
@AllArgsConstructor
public class FxRatesRequestDTO {

	private Double amount;
	private String targetCurrency;

}
