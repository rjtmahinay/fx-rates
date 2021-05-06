package com.rates.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Tristan Mahinay
 */
public class ApiError {

	@JsonProperty("status")
	private HttpStatus status;
	@JsonProperty("errorMessage")
	private String errorMessage;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
	private final LocalDateTime timeStamp;

	public ApiError(HttpStatus status, String errorMessage) {
		timeStamp = LocalDateTime.now();

		this.status = status;
		this.errorMessage = errorMessage;
	}
}
