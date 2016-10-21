package trivix.validator.dataValidation.interfaces;

import java.io.InvalidClassException;

import trivix.validator.dataValidation.errors.ValidationError;
import trivix.validator.dataValidation.exceptions.InvalidParametersException;

public interface ValidationRule {
	//public String failMessage();
	public ValidationError validationError();
	public boolean check(Object object) throws InvalidParametersException, InvalidClassException;
	public boolean isPassed();
	public boolean canApplyToType(Class type);
	public boolean canApplyToObject(Object object);
	public String getRuleCode();
	public void setParameters(String[] params);
	public boolean validParameters();
	public String[] parameters();
}
