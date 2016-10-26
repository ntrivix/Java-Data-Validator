package trivix.validator.dataValidation;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.errors.ValidationError;
import trivix.validator.dataValidation.errors.handler.ErrorHandler;
import trivix.validator.dataValidation.exceptions.InvalidParametersException;
import trivix.validator.dataValidation.interfaces.ValidationRule;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;

public abstract class AbstractValidatorInput<ObjectType, ValueType> implements ValidatorInputInterface<ObjectType, ValueType> {
	
	protected HashMap<ValidationRule, String> customMessages = new HashMap<>();
	protected HashMap<ValidationRule, String[]> ruleParams = new HashMap<>();
	protected List<ValidationRule> rules = new LinkedList<>();
	protected InputErrors errors;
	protected ObjectType objectOfValidation;
	protected String name = ":Field:";
	private Set<ErrorHandler> errorHandlers = new LinkedHashSet<>();
	
	public AbstractValidatorInput() {
		super();
		errors = new InputErrors(this);
	}

	public AbstractValidatorInput(String name, ObjectType object) {
		super();
		this.name = name;
		this.objectOfValidation = object;
	}



	@Override
	public List<ValidationRule> rules() {
		return rules;
	}

	@Override
	public void addRule(ValidationRule rule) {
		this.rules.add(rule);
		ruleParams.put(rule, rule.parameters());
		rule.validationError().setErrorCause(this);
	}

	@Override
	public void addRule(ValidationRule rule, String message) {
		this.addRule(rule);
		this.setCustomMessage(rule, message);
	}
	
	@Override
	public void setCustomMessage(ValidationRule rule, String message) {
		if (message != "" && message != null)
			customMessages.put(rule, message);		
	}
	
	protected void beforeValidation(){
		
	}

	@Override
	public final ValidatorInputInterface validate() {
		this.beforeValidation();
		for (ValidationRule validationRule : rules) {
			validationRule.setParameters(ruleParams.get(validationRule));
			try {
				if (!validationRule.check(this.getValueToValidate())){
					validationRule.validationError().setErrorCause(this);
					this.errors.addError(validationRule.validationError());
				}else
					this.errors.removeError(validationRule.validationError());
			} catch (InvalidParametersException | InvalidClassException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.afterValidation();
		errors.toConsole(customMessages);
		this.notifyErrrorHandelrs();
		return this;
	}
	


	protected void afterValidation(){
			
	}
	
	

	@Override
	public boolean isValid() {
		return errors.isEmpty();
	}

	@Override
	public InputErrors errors() {
		return errors;
	}

	@Override
	public ObjectType getObjectOfValidation() {
		return this.objectOfValidation;
	}

	@Override
	public void setObjectOfValidation(ObjectType object) {
		this.objectOfValidation = object;		
	}

	@Override
	public Map<ValidationRule,String> getErrorMessages() {
		Map<ValidationRule,String> messages = new HashMap<>();
		for (Map.Entry<ValidationRule, ValidationError> err : errors.getErrorsMap().entrySet()) {
			if (messages.containsKey(err.getKey())){	
					messages.put(err.getKey(),(customMessages.containsKey(err.getKey()) ? customMessages.get(err.getKey()) : err.getValue().getErrorMessage() ));		
			}
		}
		return messages;
	}

	@Override
	public String getInputName() {
		return name;
	}

	public void setInputName(String name) {
		this.name = name;
	}

	@Override
	public void addErrorHandler(ErrorHandler handler) {
		if (handler != null)
			errorHandlers.add(handler);
	}	
	

	private void notifyErrrorHandelrs(){
		for (ErrorHandler errorHandler : errorHandlers) {
			errorHandler.addInputErrors(this.errors);
		}
	}
	
}
