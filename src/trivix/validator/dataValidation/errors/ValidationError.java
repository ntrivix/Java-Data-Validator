package trivix.validator.dataValidation.errors;

import trivix.validator.dataValidation.interfaces.ValidationRule;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;

public interface ValidationError {
	public ValidatorInputInterface errorCausedBy();
	public ValidationRule ruleFailed();
	public String getErrorMessage();
	public void setErrorCause(ValidatorInputInterface cause);
}
