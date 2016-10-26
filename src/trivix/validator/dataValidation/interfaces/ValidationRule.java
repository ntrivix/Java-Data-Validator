package trivix.validator.dataValidation.interfaces;

import java.io.InvalidClassException;

import trivix.validator.dataValidation.errors.ValidationError;
import trivix.validator.dataValidation.exceptions.InvalidParametersException;

public interface ValidationRule {
	/**
	 * 
	 * @return ValidationError for rule object
	 */
	public ValidationError validationError();
	
	/**
	 * 
	 * Simple rule check
	 * 
	 * @param object Object that will be validated
	 * @return true if validation is passed, false if not
	 * @throws InvalidParametersException If parameters for rule are not correct InvalidParametersException is thrown
	 * @throws InvalidClassException If rule is not defined for class of object
	 */
	public boolean check(Object object) throws InvalidParametersException, InvalidClassException;
	
	/**
	 * 
	 * @return true if validation is passed, false if not
	 */
	public boolean isPassed();
	
	/**
	 * 
	 * @param type type that will be checked
	 * @return true if type can be validated with rule, otherwise false 
	 */
	public boolean canApplyToType(Class type);
	
	/**
	 * 
	 * Check if object can be validated with rule
	 * 
	 * @param object object that will be checked
	 * @return	true if type can be validated with rule, otherwise false 
	 */
	public boolean canApplyToObject(Object object);
	
	/**
	 * 
	 * @return unique rule code
	 */
	public String getRuleCode();
	
	/**
	 * 
	 * @param params rule parameters
	 */
	public void setParameters(String[] params);
	
	/**
	 * 
	 * @return true if parameters are valid, otherwise false
	 */
	public boolean validParameters();
	
	/**
	 * 
	 * @return list of parameters set on rule
	 */
	public String[] parameters();
}
