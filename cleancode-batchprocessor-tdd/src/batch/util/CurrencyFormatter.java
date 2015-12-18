package batch.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

public final class CurrencyFormatter {
    
	public static String formatWithTwoDecimals(BigDecimal number) {
		if (number == null) {
			return null;
		}
		return number.setScale(2, RoundingMode.HALF_UP).toPlainString().replace('.', ',');
	}

	public static BigDecimal parse(String number) {
		if (StringUtils.isBlank(number)) {
			return null;
		}
		return new BigDecimal(number.replace(',', '.'));
	}
}
