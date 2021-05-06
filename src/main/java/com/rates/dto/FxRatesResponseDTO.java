package com.rates.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rates.model.RateInformation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Tristan Mahinay
 */
@Data
@AllArgsConstructor
@JsonPropertyOrder(value = { "targetCurrency", "convertedAmount", "info" })
public class FxRatesResponseDTO {

	private String targetCurrency;
	private Double convertedAmount;
	private RateInformation info;
}
