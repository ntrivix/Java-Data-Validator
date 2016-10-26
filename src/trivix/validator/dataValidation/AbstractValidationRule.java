package trivix.validator.dataValidation;

import java.io.InvalidClassException;
import java.util.LinkedList;
import java.util.List;

import trivix.validator.dataValidation.errors.GenericValidationError;
import trivix.validator.dataValidation.errors.ValidationError;
import trivix.validator.dataValidation.exceptions.InvalidParametersException;
import trivix.validator.dataValidation.interfaces.ValidationRule;

public abstract class AbstractValidationRule implements ValidationRule {

	protected String[] params;
	protected ValidationError validationError;
	
	public AbstractValidationRule() {
		super();
	}

	@Override
	public final boolean canApplyToObject(Object object) {
		return this.canApplyToType(object.getClass());
	}
	

	@Override
	public final void setParameters(String[] params) {
		this.params = params;
		this.parseParams(params);
		this.validationError();	
	}
	
	protected abstract void parseParams(String[] params);
	
	protected abstract boolean validate(Object object);

	@Override
	public final boolean check(Object object) throws InvalidParametersException, InvalidClassException {
		if (!this.validParameters())
			throw new InvalidParametersException();
		if (!this.canApplyToObject(object))
			throw new InvalidClassException("Rule size can't handle this object type");
		return this.validate(object);
	}
	
	private List<Class> acceptedCl;
	public List<Class> getAcceptedClassesList(){
		if (acceptedCl == null){
			acceptedCl = new LinkedList<>();
			for (Class class1 : getAcceptedClasses()) {
				acceptedCl.add(class1);
			}
		}
		return acceptedCl;
	};
	
	public abstract Class[] getAcceptedClasses();

	@Override
	public final boolean canApplyToType(Class type) {
		return getAcceptedClassesList().contains(type);
	}	
	
	
	protected ValidationError generateValidationError(){
		return new GenericValidationError(this, errorMessage());
	};

	@Override
	public final ValidationError validationError() {
		if (validationError == null)
			validationError = generateValidationError();
		return validationError;
	}

	@Override
	public String[] parameters() {
		// TODO Auto-generated method stub
		return params;
	}
	
	protected abstract String errorMessage();
	
	
	
}
