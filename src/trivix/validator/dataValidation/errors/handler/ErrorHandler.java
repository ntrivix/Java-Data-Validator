package trivix.validator.dataValidation.errors.handler;

import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;

public interface ErrorHandler<OutputObjectType> {
	public OutputObjectType getTargetOutputObject();
	public void setTargetOutputObject(OutputObjectType output);
	//public ValidatorInputInterface getInput();
	//public void setInput(ValidatorInputInterface input);
	public void addInputErrors(InputErrors errors);
	public InputErrors[] getErrors();
	public void successState();
	public void errorState();
	public void cleanState();
}
