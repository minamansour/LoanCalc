package com.zopa.loan.util;

/**
 * *
 * <p>
 * Class which representing the logger.
 * </p>
 * 
 * @author Mina Mansour
 */
public class Logger {

	public static void consolLog(Object aObject) {
		System.out.println(String.valueOf(aObject));
	}

	public static void fileLog(Object aObject) {
		System.out.println(String.valueOf(aObject));
	}

	public static void logError(Object aObject) {
		System.err.println("[ERROR]: " + String.valueOf(aObject));
	}

	public static void logInfo(Object aObject) {
		System.out.println("[INFO]: " + String.valueOf(aObject));
	}
}
