package trivix.validator.rules;

import trivix.validator.dataValidation.AbstractValidationRule;

public class NumberRule extends AbstractValidationRule {

	private boolean passed = false;

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public String getRuleCode() {
		return "number";
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
		passed = true;
		if (object instanceof String) {
			try {
				if (object != null && !((String)object).isEmpty())
				Double.parseDouble((String) object);
			} catch (NumberFormatException e) {
				passed = false;
			}
		}
		return passed;
	}

	@Override
	public Class[] getAcceptedClasses() {
		return new Class[] { String.class, Integer.class, Double.class, Float.class };
	}

	@Override
	protected String errorMessage() {
		return ":field_name: must be a number";
	}

}
