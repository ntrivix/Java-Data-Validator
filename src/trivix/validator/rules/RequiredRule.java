package trivix.validator.rules;

import java.io.File;

import trivix.validator.dataValidation.AbstractValidationRule;
import trivix.validator.dataValidation.errors.ValidationError;

public class RequiredRule extends AbstractValidationRule {

	private boolean passed = false;

	@Override
	public boolean isPassed() {
		// TODO Auto-generated method stub
		return passed;
	}

	@Override
	public String getRuleCode() {
		return "required";
	}

	@Override
	public boolean validParameters() {
		return true;
	}

	@Override
	protected void parseParams(String[] params) {

	}

	@Override
	protected boolean validate(Object object) {
		passed = false;
		if (object instanceof String)
			passed = !((String) object).isEmpty();
		else if (object instanceof File)
			passed = ((File) object).exists();
		else if (object != null) // U ovom slucaju je sigurno broj
			passed = true;
		return passed;
	}

	@Override
	public Class[] getAcceptedClasses() {
		return new Class[] { File.class, String.class, Integer.class, Double.class, Float.class };
	}

	@Override
	protected String errorMessage() {
		return ":field_name: is required";
	}

}
