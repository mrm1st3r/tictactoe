package com.github.mrm1st3r.ttt.util.input;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class Kbd {
	
	private static String read()
	{
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			return in.readLine();
		} catch(Exception e) {
			return "";
		}
	}
	
	public static int readInt()
	{
		return Integer.parseInt(Kbd.read());
	}
	
	/**
	 * @todo add methods for other datatypes (double, ...)
	 */
}
