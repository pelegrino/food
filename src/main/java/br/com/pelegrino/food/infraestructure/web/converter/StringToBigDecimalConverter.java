package br.com.pelegrino.food.infraestructure.web.converter;

import java.math.BigDecimal;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		
		source = source.replace(",", ".").trim();
		return BigDecimal.valueOf(Double.valueOf(source));
	}


	
}
