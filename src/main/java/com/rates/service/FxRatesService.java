package com.rates.service;

import com.rates.dto.FxRatesRequestDTO;
import com.rates.dto.FxRatesResponseDTO;

/**
 * @author Tristan Mahinay
 */
public interface FxRatesService {

	FxRatesResponseDTO getConvertedAmount(FxRatesRequestDTO requestDTO);
}
