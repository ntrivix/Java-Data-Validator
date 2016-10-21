package trivix.validator.dataValidation;

import trivix.validator.Validator;
import trivix.validator.dataValidation.interfaces.ValidationRule;

public class ValidationRuleFacade {

	public static ValidationRule getValidator(String ruleStr){
		String[] ruleSplit = ruleStr.split(":");
		String ruleCode = ruleSplit[0];
		String[] ruleParams = {};
		if (ruleSplit.length > 1) {
			ruleParams = ruleSplit[1].split(",");
		}
		ValidationRule rule = Validator.getAvailableRulesMap().get(ruleCode);
		rule.setParameters(ruleParams);
		return rule;
	}
	

}
