package trivix.validator.dataValidation.interfaces;

import java.util.List;

import trivix.validator.Validator;
import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.exceptions.ValidatorInputNotResolved;

public interface ValidatorInterface {
	public ValidatorInterface validateAll();
	public boolean isValid();
	public Validator add(Object toValidate, String fieldName, String[] rules) throws ValidatorInputNotResolved;
	public Validator add(Object toValidate, String fieldName, String[] rules, String[] customMessages) throws ValidatorInputNotResolved;
	public Validator add(ValidatorInputInterface input);
	public List<ValidatorInputInterface> getFields();
	public List<ValidatorInputInterface> getInvalidFields();
	public InputErrors[] errors();
}
