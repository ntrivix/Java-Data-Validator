package trivix.validator.dataValidation.errors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import trivix.validator.dataValidation.interfaces.ValidationRule;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;

public class InputErrors {

	private ValidatorInputInterface input;
	Map<ValidationRule,ValidationError> errors = new HashMap<>();
	
	public InputErrors(ValidatorInputInterface input) {
		this.input = input;
	}
	
	public void addError(ValidationError e){
		errors.put(e.ruleFailed(), e);
		
	}
	
	public void removeError(ValidationError e) {
		errors.remove(e.ruleFailed());
		
	}

	public List<ValidationError> getErrors() {
		List<ValidationError> err = new LinkedList<>();
		for (ValidationRule key : errors.keySet()) {
			err.add(errors.get(key));
		}
		return err;
	}
	
	public Map<ValidationRule,ValidationError> getErrorsMap() {
		return errors;
	}
	
	public boolean isEmpty(){
		return errors.isEmpty();
	}
	
	public int size(){
		return errors.size();
	}
	
	public void toConsole(HashMap<ValidationRule, String> customMessages){
		for (ValidationError validationError : getErrors()) {
			if (customMessages.containsKey(validationError.ruleFailed()))
					System.err.println(customMessages.get(validationError.ruleFailed()));
			else
				System.err.println(validationError.getErrorMessage());
		}
	}
	
	
}
