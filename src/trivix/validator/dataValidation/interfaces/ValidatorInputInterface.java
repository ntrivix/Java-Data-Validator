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
	public void setCustomMessage(ValidationRule rule, String message);
	

	public ObjectType getObjectOfValidation();
	public void setObjectOfValidation(ObjectType object);
	public ValueType getValueToValidate();
	public Class<ValueType> getValueType();
	public void addErrorHandler(ErrorHandler handler);
	
	public ValidatorInputInterface validate();
	public boolean isValid();
	
	public Map<ValidationRule, String> getErrorMessages();
	public InputErrors errors();
	
	public void setOnChangeValidate(boolean onchange);
//metoda kojom cu dodati error handler	
}
