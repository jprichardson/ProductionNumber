package com.reflect7.productionnumber.shared.lib.util;

public class MathUtil {
	public static float round(float num, int precision){
		double mult = Math.pow(10, precision);
		double d = num * mult;
		long i = (long)d;
		d = i / mult;
		return (float)d;
	}
}
