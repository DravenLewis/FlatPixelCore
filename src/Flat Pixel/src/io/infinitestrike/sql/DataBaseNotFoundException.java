package io.infinitestrike.sql;

public class DataBaseNotFoundException extends Exception{

	private static final long serialVersionUID = -6646284444501174922L;

	public DataBaseNotFoundException(String m){
		super(m);
	}
}
