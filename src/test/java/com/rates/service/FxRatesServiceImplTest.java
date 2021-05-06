package com.rates.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.rates.dto.FxRatesRequestDTO;
import com.rates.dto.LatestRateResponseDTO;
import com.rates.dto.SymbolsResponseDTO;
import com.rates.exception.ElementNotFoundException;
import com.rates.service.impl.FxRatesServiceImpl;

/**
 * @author Tristan Mahinay
 */
@ExtendWith(MockitoExtension.class)
class FxRatesServiceImplTest {

	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	private FxRatesService service = new FxRatesServiceImpl();

	@Test
	void testGetConvertAmountSuccess() {
		ReflectionTestUtils.setField(service, "exchangeRateLatestEndpoint", "http://api.exchangeratesapi.io/v1/latest",
				String.class);
		ReflectionTestUtils.setField(service, "apiKey", "b784925fb24907214cb2e2ccba06f771", String.class);
		ReflectionTestUtils.setField(service, "exchangeRateSymbolsEndpoint",
				"http://api.exchangeratesapi.io/v1/symbols", String.class);

		Map<String, Object> rates = new HashMap<>();
		rates.put("PHP", 57.804048);

		Map<String, Object> symbols = new HashMap<>();
		symbols.put("PHP", "Philippine Peso");

		when(restTemplate.getForObject(
				"http://api.exchangeratesapi.io/v1/latest?access_key=b784925fb24907214cb2e2ccba06f771&symbols=PHP",
				LatestRateResponseDTO.class))
						.thenReturn(new LatestRateResponseDTO(1232131, "EUR", "2021-05-06", rates, true));

		when(restTemplate.getForObject(
				"http://api.exchangeratesapi.io/v1/symbols?access_key=b784925fb24907214cb2e2ccba06f771",
				SymbolsResponseDTO.class)).thenReturn(new SymbolsResponseDTO(true, symbols));

		FxRatesRequestDTO requestDTO = new FxRatesRequestDTO(Double.valueOf(78), "PHP");

		assertNotNull(service.getConvertedAmount(requestDTO));

	}

	@Test
	void testGetConvertAmountSymbolsNotFound() {
		ReflectionTestUtils.setField(service, "exchangeRateLatestEndpoint", "http://api.exchangeratesapi.io/v1/latest",
				String.class);
		ReflectionTestUtils.setField(service, "apiKey", "b784925fb24907214cb2e2ccba06f771", String.class);
		ReflectionTestUtils.setField(service, "exchangeRateSymbolsEndpoint",
				"http://api.exchangeratesapi.io/v1/symbols", String.class);

		Map<String, Object> rates = new HashMap<>();
		rates.put("PHP", 57.804048);

		Map<String, Object> symbols = new HashMap<>();
		symbols.put("PHP", "Philippine Peso");

		FxRatesRequestDTO requestDTO = new FxRatesRequestDTO(Double.valueOf(10), "PHP");

		when(restTemplate.getForObject(
				"http://api.exchangeratesapi.io/v1/symbols?access_key=b784925fb24907214cb2e2ccba06f771",
				SymbolsResponseDTO.class)).thenReturn(null);

		assertThrows(ElementNotFoundException.class, () -> service.getConvertedAmount(requestDTO));

	}

	@Test
	void testGetConvertAmountTargetCurrencyNotFound() {
		ReflectionTestUtils.setField(service, "exchangeRateLatestEndpoint", "http://api.exchangeratesapi.io/v1/latest",
				String.class);
		ReflectionTestUtils.setField(service, "apiKey", "b784925fb24907214cb2e2ccba06f771", String.class);
		ReflectionTestUtils.setField(service, "exchangeRateSymbolsEndpoint",
				"http://api.exchangeratesapi.io/v1/symbols", String.class);

		Map<String, Object> rates = new HashMap<>();
		rates.put("PHP", 57.804048);

		Map<String, Object> symbols = new HashMap<>();
		symbols.put("PHP", "Philippine Peso");

		FxRatesRequestDTO requestDTO = new FxRatesRequestDTO(Double.valueOf(10), "USD");

		when(restTemplate.getForObject(
				"http://api.exchangeratesapi.io/v1/symbols?access_key=b784925fb24907214cb2e2ccba06f771",
				SymbolsResponseDTO.class)).thenReturn(new SymbolsResponseDTO(true, symbols));

		assertThrows(ElementNotFoundException.class, () -> service.getConvertedAmount(requestDTO));

	}

	@Test
	void testGetConvertAmountLatestRatesNotFound() {
		ReflectionTestUtils.setField(service, "exchangeRateLatestEndpoint", "http://api.exchangeratesapi.io/v1/latest",
				String.class);
		ReflectionTestUtils.setField(service, "apiKey", "b784925fb24907214cb2e2ccba06f771", String.class);
		ReflectionTestUtils.setField(service, "exchangeRateSymbolsEndpoint",
				"http://api.exchangeratesapi.io/v1/symbols", String.class);

		Map<String, Object> rates = new HashMap<>();
		rates.put("PHP", 57.804048);

		Map<String, Object> symbols = new HashMap<>();
		symbols.put("PHP", "Philippine Peso");

		FxRatesRequestDTO requestDTO = new FxRatesRequestDTO(Double.valueOf(10), "PHP");

		when(restTemplate.getForObject(
				"http://api.exchangeratesapi.io/v1/symbols?access_key=b784925fb24907214cb2e2ccba06f771",
				SymbolsResponseDTO.class)).thenReturn(new SymbolsResponseDTO(true, symbols));

		when(restTemplate.getForObject(
				"http://api.exchangeratesapi.io/v1/latest?access_key=b784925fb24907214cb2e2ccba06f771&symbols=PHP",
				LatestRateResponseDTO.class)).thenReturn(null);

		assertThrows(ElementNotFoundException.class, () -> service.getConvertedAmount(requestDTO));

	}
}
