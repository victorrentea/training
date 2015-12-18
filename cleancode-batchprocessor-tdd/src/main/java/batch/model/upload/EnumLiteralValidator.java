package batch.model.upload;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.validator.Validator;

public class EnumLiteralValidator implements Validator<EnumLiteral> {
	
	private Set<String> allowedValues = new LinkedHashSet<String>();
	
	@Override
	public void initialize(EnumLiteral annotation) {
		for (Enum<?> enumValue : annotation.value().getEnumConstants()) {
			allowedValues.add(enumValue.name());
		}
	}

	@Override
	public boolean isValid(Object value) {
		return value==null || allowedValues.contains(value);
	}


}
