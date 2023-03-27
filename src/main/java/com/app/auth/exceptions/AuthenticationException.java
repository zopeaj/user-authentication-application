package com.app.auth.exceptions;

public class AuthenticationException extends RuntimeException{
	private static final long serialVersionUID = -6022107646690473468L;
	
	public AuthenticationException(String msg, Throwable t) { super(msg, t);  }
	public AuthenticationException(String msg) { super(msg); } 
}
