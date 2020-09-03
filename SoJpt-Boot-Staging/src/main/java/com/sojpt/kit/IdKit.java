package com.sojpt.kit;

import java.util.UUID;

public class IdKit {
	
	public static String generatorID() {
		return UUID.randomUUID().toString().replaceAll("-", ""); 
	}

	public static void main(String[] args) {
		System.out.println(generatorID().length());
	}
}
