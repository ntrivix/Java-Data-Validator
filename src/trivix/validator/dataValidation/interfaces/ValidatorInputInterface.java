package trivix.validator.dataValidation.interfaces;

import java.util.List;
import java.util.Map;

import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.errors.handler.ErrorHandler;

public interface ValidatorInputInterface<ObjectType,ValueType> {
	
	public void setInputName(String name);
	public String getInputName();
	
	public List<ValidationRule> rules();
	public void addRule(ValidationRule rule);
	public void addRule(ValidationRule rule, String message);
	/**
	 * Set custom message for given rule
	 * @param rule rule object
	 * @param message custom message
	 */
	public void setCustomMessage(ValidationRule rule, String message);
	
	/**
	 * 
	 * @return object thath will be validated
	 */
	public ObjectType getObjectOfValidation();
	public void setObjectOfValidation(ObjectType object);
	
	/**
	 * 
	 * @return return raw data value from validation object
	 */
	public ValueType getValueToValidate();
	/**
	 * 
	 * @return raw data value type
	 */
	public Class<ValueType> getValueType();
	
	/**
	 * Add new object that would handle validation errors
	 * @param handler new error handler
	 */
	public void addErrorHandler(ErrorHandler handler);
	
	
	/**
	 * Validate input for given rules
	 * @return self instance
	 */
	public ValidatorInputInterface validate();
	/**
	 * 
	 * @return true if validation is passed, otherwise false
	 */
	public boolean isValid();
	
	public Map<ValidationRule, String> getErrorMessages();
	public InputErrors errors();
	
	
	/**
	 * Validate input on some event
	 * @param onchange if true - validation input will be validated on every event 
	 */
	public void setOnChangeValidate(boolean onchange);	
}
