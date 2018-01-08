package com.ts.urbanspoon.exception;

public class UrbanspoonException extends Exception {
	String message;
	
	public UrbanspoonException() {
		// TODO Auto-generated constructor stub
	}
	public UrbanspoonException(String message) {
		this.message = message;
		
	}
	@Override
	public String toString() {
		return "UrbanspoonException [message=" + message + "]";
	}
	
}
