package trivix.validator.dataValidation.errors.handler;

import java.util.Set;

import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;

public interface ErrorHandler<OutputObjectType> {
	/**
	 * 
	 * @return object where error will be shown
	 */
	public OutputObjectType getTargetOutputObject();
	
	/**
	 * 
	 * @param output object where error will be shown
	 */
	public void setTargetOutputObject(OutputObjectType output);
	
	/**
	 * 
	 * @param errors add error
	 */
	public void addInputErrors(InputErrors errors);
	/**
	 * 
	 * @return set of validation errors
	 */
	public Set<InputErrors> getErrors();
	/**
	 * handle validation successful state
	 */
	public void successState();
	/**
	 * Handle validation error state
	 */
	public void errorState();
	
	/**
	 * Handle validation clear state
	 */
	public void cleanState();
}
