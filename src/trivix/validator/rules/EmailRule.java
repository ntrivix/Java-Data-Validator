package trivix.validator.rules;

import trivix.validator.dataValidation.AbstractValidationRule;
import trivix.validator.dataValidation.errors.GenericValidationError;
import trivix.validator.dataValidation.errors.ValidationError;

public class EmailRule extends AbstractValidationRule {

	private Class[] acceptedClasses = { String.class };
	private boolean passed = false;
	@Override
	public boolean isPassed() {
			return passed;
	}

	@Override
	public String getRuleCode() {
		return "mail";
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
		if (object instanceof String) {
			String mail = (String) object;
			String ePattern = "^()|([a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,})))$";
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
			java.util.regex.Matcher m = p.matcher(mail);
			if (m.matches()){
				passed = true;
				return true;
			}
		}
		passed = false;
		return false;
	}

	@Override
	public Class[] getAcceptedClasses() {
		return acceptedClasses;
	}

	@Override
	protected String errorMessage() {
		return "Email is not valid!";
	}

}
