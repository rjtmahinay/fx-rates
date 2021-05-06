package com.rates.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Tristan Mahinay
 */
@Getter
@AllArgsConstructor
@ToString
public class SymbolsResponseDTO {

	private boolean success;
	private Map<String, Object> symbols;
}
