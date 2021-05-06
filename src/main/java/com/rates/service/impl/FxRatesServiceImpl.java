package com.rates.service.impl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rates.dto.FxRatesRequestDTO;
import com.rates.dto.FxRatesResponseDTO;
import com.rates.dto.LatestRateResponseDTO;
import com.rates.dto.SymbolsResponseDTO;
import com.rates.exception.ElementNotFoundException;
import com.rates.model.RateInformation;
import com.rates.service.FxRatesService;
import com.rates.utility.Constants;

/**
 * @author Tristan Mahinay
 */
@Service
public class FxRatesServiceImpl implements FxRatesService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${exchange.latest.endpoint}")
	private String exchangeRateLatestEndpoint;

	@Value("${exchange.symbols.endpoint}")
	private String exchangeRateSymbolsEndpoint;

	@Value("${api.key}")
	private String apiKey;

	@Override
	public FxRatesResponseDTO getConvertedAmount(FxRatesRequestDTO requestDTO) {

		String targetCurrency = requestDTO.getTargetCurrency();

		validate(targetCurrency);

		String latestEndpoint = constructLatestEndpoint(targetCurrency);

		LatestRateResponseDTO latestRateDTO = restTemplate.getForObject(latestEndpoint, LatestRateResponseDTO.class);

		if (Objects.isNull(latestRateDTO))
			throw new ElementNotFoundException(Constants.LATEST_RATES_NOT_FOUND);

		Double convertedValue = (Double) latestRateDTO.getRates().get(targetCurrency);
		Double convertedAmount = requestDTO.getAmount() * convertedValue;

		return new FxRatesResponseDTO(targetCurrency, convertedAmount, getRates(targetCurrency, convertedValue));
	}

	private String constructLatestEndpoint(String targetCurrency) {

		return UriComponentsBuilder.fromUriString(exchangeRateLatestEndpoint)
				.queryParam(Constants.ACCESS_KEY_PARAM, apiKey).queryParam(Constants.SYMBOLS_PARAM, targetCurrency)
				.build().toString();
	}

	private RateInformation getRates(String targetCurrency, Double targetRateAmount) {
		RateInformation rates = new RateInformation();
		rates.setBaseCurrency(Constants.EURO);
		rates.setBaseRateAmount(Double.valueOf(1));
		rates.setTargetCurrency(targetCurrency);
		rates.setTargetRateAmount(targetRateAmount);

		return rates;
	}

	private void validate(String targetCurrency) {
		String symbolsEndpoint = constructSymbolsEndpoint();
		SymbolsResponseDTO symbolsDTO = restTemplate.getForObject(symbolsEndpoint, SymbolsResponseDTO.class);

		if (Objects.isNull(symbolsDTO))
			throw new ElementNotFoundException(Constants.SYMBOLS_NOT_FOUND);

		Map<String, Object> symbolsMap = symbolsDTO.getSymbols();

		if (!symbolsMap.containsKey(targetCurrency))
			throw new ElementNotFoundException(Constants.TARGET_CURRENCY_NOT_FOUND);

	}

	private String constructSymbolsEndpoint() {
		return UriComponentsBuilder.fromUriString(exchangeRateSymbolsEndpoint)
				.queryParam(Constants.ACCESS_KEY_PARAM, apiKey).build().toString();
	}

}
