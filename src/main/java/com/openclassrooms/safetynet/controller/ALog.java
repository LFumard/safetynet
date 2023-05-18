package com.openclassrooms.safetynet.controller;

import java.io.IOException;
import java.util.logging.FileHandler;

public class ALog {

	private static FileHandler handler = null;
	
	public static FileHandler getFileHandler() {
		if (handler == null) {
			try {
				handler = new FileHandler("SafetyLog.txt", true);
				} 	catch (SecurityException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
		}
		return handler;
	}
}
