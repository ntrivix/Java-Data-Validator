package trivix.validator.dataValidation;

import trivix.validator.Validator;
import trivix.validator.dataValidation.exceptions.RuleNotExists;
import trivix.validator.dataValidation.interfaces.ValidationRule;

public class ValidationRuleFactory {

	/**
	 * 
	 * @param ruleStr rule string in format ruleCode:param1,param2...
	 * @return validation rule for given string
	 * @throws RuleNotExists
	 */
	public static ValidationRule getValidationRule(String ruleStr) throws RuleNotExists{
		String[] ruleSplit = ruleStr.split(":");
		String ruleCode = ruleSplit[0];
		String[] ruleParams = {};
		if (ruleSplit.length > 1) {
			ruleParams = ruleSplit[1].split(",");
		}
		ValidationRule rule = Validator.getAvailableRulesMap().get(ruleCode);
		if (rule == null)
			throw new RuleNotExists(ruleCode);
		rule.setParameters(ruleParams);
		return rule;
	}
	

}
