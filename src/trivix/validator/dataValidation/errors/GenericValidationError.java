package trivix.validator.dataValidation.errors;

import trivix.validator.dataValidation.interfaces.ValidationRule;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;

public class GenericValidationError implements ValidationError {

	private ValidationRule rule;
	private String message;
	private ValidatorInputInterface cause;

	public GenericValidationError(ValidationRule rule, String message) {
		super();
		this.rule = rule;
		this.message = message;
	}

	@Override
	public ValidationRule ruleFailed() {
		return rule;
	}

	@Override
	public String getErrorMessage() {
		return parseMessage(message);
	}

	protected String parseMessage(String message) {
		if (cause != null)
			message = message.replaceAll(":field_name:", cause.getInputName());
		return message.replaceAll(":rule:", rule.getRuleCode());
	}

	public void setErrorCause(ValidatorInputInterface cause) {
		this.cause = cause;
	}

	@Override
	public ValidatorInputInterface errorCausedBy() {
		return cause;
	}

}
