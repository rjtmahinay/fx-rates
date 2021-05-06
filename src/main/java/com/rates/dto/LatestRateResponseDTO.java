package com.rates.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tristan Mahinay
 */
@Getter
@AllArgsConstructor
public class LatestRateResponseDTO {

	private Integer timeStamp;
	private String base;
	private String date;
	private Map<String, Object> rates;
	private boolean success;
}
