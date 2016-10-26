package trivix.validator.dataValidation.exceptions;

import jdk.nashorn.internal.objects.annotations.Constructor;

public class RuleNotExists extends Exception {

	private String ruleCode;

	public RuleNotExists(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	@Override
	public String getMessage() {
		return "Rule with code "+ruleCode+" does not exists!";
	}
	
	
}
