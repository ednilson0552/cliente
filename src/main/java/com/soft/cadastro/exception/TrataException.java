package com.soft.cadastro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TrataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TrataException(String message) {
		super(message);
	}
	
}

