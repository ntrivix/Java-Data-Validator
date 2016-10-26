package trivix.validator.dataValidation;

import java.util.List;

import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;

public class PrimitiveValidatorInput<Type> extends AbstractValidatorInput<Type, Type> {


	@Override
	public Type getValueToValidate() {
		return objectOfValidation;
	}

	@Override
	public Class<Type> getValueType() {
		return (Class<Type>) objectOfValidation.getClass();
	}

	@Override
	public void setOnChangeValidate(boolean onchange) {
		// TODO Auto-generated method stub
		
	}

	

}
