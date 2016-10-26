package trivix.validator.dataValidation.errors.handler;

import java.util.LinkedHashSet;
import java.util.Set;

import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;

public abstract class AbstractErrorHandler<OutputObjectType> implements ErrorHandler<OutputObjectType> {

	private OutputObjectType output;
	private Set<InputErrors> errors = new LinkedHashSet<>();
	
	public AbstractErrorHandler(OutputObjectType output) {
		super();
		this.output = output;
	}

	@Override
	public OutputObjectType getTargetOutputObject() {
		return output;
	}


	@Override
	public void setTargetOutputObject(OutputObjectType output) {
		this.output = output;
	}



	@Override
	public Set<InputErrors> getErrors() {
		return errors;
	}

	@Override
	public final void addInputErrors(InputErrors errors) {
		this.cleanState();
		this.errors.add(errors);
		if (errors.isEmpty())
			this.errors.remove(errors);
		if(this.errors.isEmpty())
			this.successState();
		else
			this.errorState();
	}

	@Override
	public String toString() {
		StringBuilder e = new StringBuilder();
		for (InputErrors inputErrors : errors) {
			e.append(inputErrors.toString());
		}
		return e.toString();
	}
	
	
	
	
	
}
