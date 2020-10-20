package io.infinitestrike.core;

public class FPError {
	public int code = 0;
	public String message = "";
	
	public FPError() {
		this(0,"OK");
	}
	
	public FPError(String message) {
		this(1,message);
	}
	
	public FPError(int code) {
		this(code,"An Unknown Error Occured");
	}
	
	public FPError(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String toString() {
		return String.format("[%s] =: %s", code, message);
	}
}
