package utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class BigDecimalUtils {
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
	
	public static String printDecimalWithLocale(BigDecimal bigDecimal, Locale locale, int scale){
		String result = "";
		bigDecimal = bigDecimal.setScale(scale,ROUNDING_MODE);
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		double money = bigDecimal.doubleValue();
		result = nf.format(money);
		return result;
	}
	
	public static String printDecimal(BigDecimal bigDecimal, int scale){
		return printDecimalWithLocale(bigDecimal, Locale.FRENCH, scale);
	}
}
