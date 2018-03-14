package com.ahpu.erp.utils;

import java.util.UUID;

public class UUIDutil {
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
