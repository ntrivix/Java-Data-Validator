package trivix.validator.rules;

import trivix.validator.dataValidation.AbstractValidationRule;

public class InRule extends AbstractValidationRule{
	
	private boolean passed = false;

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public String getRuleCode() {
		// TODO Auto-generated method stub
		return "in";
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
		for (String string : params) {
			if (string.compareTo(object.toString()) == 0){
				return passed = true;
			}
		}
		return passed = false;
	}

	@Override
	public Class[] getAcceptedClasses() {
		return new Class[]{String.class, Integer.class};
	}

	@Override
	protected String errorMessage() {
		StringBuilder values = new StringBuilder();
		for (String string : params) {
			values.append(string+", ");
		}
		return ":field_name: must be some of these values: "+values.toString();
	}

}
