package com.rates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rates.dto.FxRatesRequestDTO;
import com.rates.dto.FxRatesResponseDTO;
import com.rates.service.FxRatesService;

/**
 * @author Tristan Mahinay
 */
@RestController
public class FxRatesController {

	@Autowired
	FxRatesService fxRatesService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/convertAmount")
	public ResponseEntity<FxRatesResponseDTO> convertAmount(@RequestBody FxRatesRequestDTO requestDTO) {

		FxRatesResponseDTO responseDTO = fxRatesService.getConvertedAmount(requestDTO);

		return ResponseEntity.ok(responseDTO);
	}
}
