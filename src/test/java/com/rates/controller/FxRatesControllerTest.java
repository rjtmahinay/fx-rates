package com.rates.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rates.dto.FxRatesRequestDTO;
import com.rates.dto.FxRatesResponseDTO;
import com.rates.model.RateInformation;
import com.rates.service.FxRatesService;
import com.rates.utility.Constants;

/**
 * @author Tristan Mahinay
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FxRatesController.class)
class FxRatesControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	FxRatesService service;

	@MockBean
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper mapper;

	@Test
	void testConvertAmount() throws Exception {

		FxRatesRequestDTO requestDTO = new FxRatesRequestDTO(Double.valueOf(78), "JPY");

		FxRatesResponseDTO responseDTO = new FxRatesResponseDTO("JPY", Double.valueOf(10224.76338),
				getRates("JPY", Double.valueOf(131.08671)));

		when(service.getConvertedAmount(requestDTO)).thenReturn(responseDTO);

		mockMvc.perform(post("/convertAmount").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(requestDTO))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

	}

	private RateInformation getRates(String targetCurrency, Double targetRateAmount) {
		RateInformation rates = new RateInformation();
		rates.setBaseCurrency(Constants.EURO);
		rates.setBaseRateAmount(Double.valueOf(1));
		rates.setTargetCurrency(targetCurrency);
		rates.setTargetRateAmount(targetRateAmount);

		return rates;
	}

}
