package trivix.validator.rules;

import java.io.File;
import java.util.List;

import trivix.validator.dataValidation.AbstractValidationRule;
import trivix.validator.dataValidation.errors.GenericValidationError;
import trivix.validator.dataValidation.errors.ValidationError;
import trivix.validator.dataValidation.interfaces.ValidationRule;

public class SizeRule extends AbstractValidationRule {

	private final Class[] acceptedClasses = { String.class, File.class, Integer.class, Double.class, Float.class };

	private int downLimit;
	private int upLimit;
	private boolean passed = false;

	private boolean validParams = false;

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public String getRuleCode() {
		return "size";
	}

	@Override
	public boolean validParameters() {
		return validParams;
	}

	@Override
	protected boolean validate(Object object) {
		passed = false;
		if (object instanceof File){
			File f = (File) object;
			if (f.length() < downLimit || f.length() > upLimit)
				return false;
			passed = true;
		}
		if (object instanceof String){
			String s = (String) object;
			if (s.length() < downLimit || s.length() > upLimit)
				return false;
			passed = true;
		}
		if (!passed && ((Double)object < downLimit || (Double)object > upLimit))
			return false;
		passed = true;
		return true;
	}
	
	

	@Override
	public Class[] getAcceptedClasses() {
		return acceptedClasses;
	}

	@Override
	public String errorMessage() {
		return "Size of :field_name: must be between " + downLimit + "-" + upLimit;
	}

	@Override
	protected void parseParams(String[] params) {
		if (params.length > 1) {
			try {
				downLimit = Integer.parseInt(params[0]);
				upLimit = Integer.parseInt(params[1]);
				validParams = true;
				return;
			} catch (NumberFormatException e) {
				validParams = false;
			}
		}
		validParams = false;
	}

}
