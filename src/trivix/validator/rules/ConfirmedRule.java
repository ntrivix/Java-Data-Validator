package trivix.validator.rules;

import java.io.File;

import javax.swing.JCheckBox;

import trivix.validator.dataValidation.AbstractValidationRule;

public class ConfirmedRule extends AbstractValidationRule {

	private boolean passed = false;

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public String getRuleCode() {
		return "confirmed";
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
		if ((boolean)object)
			passed = true;
		else
			passed = false;
		return passed;
	}

	@Override
	public Class[] getAcceptedClasses() {
		return new Class[] { Boolean.class };
	}

	@Override
	protected String errorMessage() {
		return ":field_name: must be confirmed";
	}

}
