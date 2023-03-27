package br.com.pelegrino.food.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
	
	private static final Locale LOCALE_BRASIL = new Locale("pt", "BR");
	
	public static NumberFormat newCurrencyFormat() {
		NumberFormat nf = NumberFormat.getNumberInstance(LOCALE_BRASIL);
	
		nf.setMaximumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
		
		return nf;
		
	}
	
	public static String formatCurrency(BigDecimal number) {
		if (number == null) {
			return null;
		}
		
		return newCurrencyFormat().format(number);

		
	}
	
}
