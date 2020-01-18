package com.desafio.carusersystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Unauthorized!")
public class ExceptionUnauthorized extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ExceptionUnauthorized(String msg){
		super(msg);
	}
}