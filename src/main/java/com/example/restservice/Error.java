package com.example.restservice;

public class Error {

	public final String errorMessage;
	public final int code;

	public Error(String message, int respCode) {
		this.errorMessage = message;
		this.code = respCode;
	}

}
