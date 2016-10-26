package trivix.validator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import trivix.validator.dataValidation.ValidationRuleFactory;
import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.errors.handler.ErrorHandler;
import trivix.validator.dataValidation.exceptions.ErrorHandlerNotResolved;
import trivix.validator.dataValidation.exceptions.RuleNotExists;
import trivix.validator.dataValidation.exceptions.ValidatorInputNotResolved;
import trivix.validator.dataValidation.interfaces.ValidationRule;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;
import trivix.validator.errorHandlers.TextualPanelErrorHandler;
import trivix.validator.errorHandlers.SimplePanelErrorHandler;

public class Validator {
	private ArrayList<ValidatorInputInterface> inputFields = new ArrayList<>();
	private boolean valid = false;

	private static boolean isBooted = false;
	private static HashMap<String, ValidationRule> avaiableRules = new HashMap<>();

	private static HashMap<Class, Class> registerdInputs = new HashMap<>();
	private static HashMap<Class, Class> registerdInputsOnInstance = new HashMap<>();

	private Class defaultFormErrorHandler = TextualPanelErrorHandler.class;
	private Class defaultInputErrorHandler = SimplePanelErrorHandler.class;
	private ErrorHandler formErrorHandler;

	/**
	 * Register new validator input class globaly
	 * 
	 * @param realInputClass
	 *            Java class that will be mapped to resolver class
	 * @param resolverClass
	 *            Class that implement ValidatorInputInterface
	 * 
	 */
	public static void registerInputClass(Class realInputClass, Class resolverClass) {
		registerdInputs.put(realInputClass, resolverClass);
	}

	/**
	 * Register new validator input class for single validator instance
	 * 
	 * @param realInputClass Class of real input (JTextField, JTextArea ...)
	 * @param resolverClass Class that will resolve validation for realInputClass (must implement ValidatorInputInterface)
	 */
	public void registerInputClassOnInstance(Class realInputClass, Class resolverClass) {
		registerdInputsOnInstance.put(realInputClass, resolverClass);
	}

	/**
	 * Register new validation rule
	 * 
	 * @param rule validation rule
	 */
	public static void registerRule(ValidationRule rule) {
		Validator.avaiableRules.put(rule.getRuleCode(), rule);
	}

	/**
	 * 
	 * @return List of all available rules
	 */
	public static List<ValidationRule> getAvailableRules() {
		List<ValidationRule> rules = new LinkedList<>();
		for (String ruleKey : avaiableRules.keySet()) {
			rules.add(avaiableRules.get(ruleKey));
		}
		return rules;
	}

	public static Map<String, ValidationRule> getAvailableRulesMap() {
		return avaiableRules;
	}

	/**
	 * 
	 * @return Instance of new validator object
	 */
	public static Validator validate() {
		return new Validator();
	}

	/**
	 * Simple validation
	 * 
	 * @param object object that should pass validation
	 * @param rules rules for validation
	 * @return array of error or empty array if validation is passed
	 * @throws ValidatorInputNotResolved
	 * @throws RuleNotExists
	 */
	public static InputErrors[] validate(Object object, String[] rules) throws ValidatorInputNotResolved, RuleNotExists {
		return Validator.validate().add(object, Object.class.getName(), rules).validateAll().errors();
	}

	
	/**
	 * 
	 * @return validation errors
	 */
	public InputErrors[] errors() {
		List<InputErrors> e = new LinkedList<>();
		for (ValidatorInputInterface input : inputFields) {
			if (!input.isValid())
				e.add(input.errors());
		}
		return e.toArray(new InputErrors[e.size()]);
	}

	private Validator() {
		if (!Validator.isBooted) {
			ValidatorBootstrap.boot();
			Validator.isBooted = true;
		}
	}

	public Validator add(Object toValidate, String fieldName, String[] rules) throws ValidatorInputNotResolved, RuleNotExists {
		return this.add(toValidate, fieldName, rules, null);
	}

	

	
	
	public Validator add(Object toValidate, String fieldName, String[] rules, String[] customMessages,
			Object errorContainer) throws  ValidatorInputNotResolved, RuleNotExists, ErrorHandlerNotResolved {
		ErrorHandler errorHandler = null;
		try {
			Constructor<?> cons;
			cons = defaultInputErrorHandler.getConstructor(errorContainer.getClass());
			errorHandler = (ErrorHandler) cons.newInstance(errorContainer);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ErrorHandlerNotResolved(defaultInputErrorHandler, errorContainer);
		}
		
		return this.add(toValidate, fieldName, rules, customMessages, errorHandler);

	}

	
	public Validator add(Object toValidate, String fieldName, String[] rules, Object errorContainer)
			throws ValidatorInputNotResolved, RuleNotExists, ErrorHandlerNotResolved {
		return this.add(toValidate, fieldName, rules, null, errorContainer);
	}
	
	/**
	 * 
	 * @param toValidateobject that should be validated
	 * @param fieldName This name will be used when building error message
	 * @param rules List of rules in format ruleCode:parameter1,parameter2...
	 * @param customMessages custom error messages, respect to order of the rules
	 * @return
	 * @throws ValidatorInputNotResolved
	 * @throws RuleNotExists
	 */
	public Validator add(Object toValidate, String fieldName, String[] rules, String[] customMessages)
			throws ValidatorInputNotResolved, RuleNotExists {
		return this.add(toValidate, fieldName, rules, customMessages, null);
	}
	
	/**
	 * 
	 * @param toValidate object that should be validated
	 * @param fieldName This name will be used when building error message
	 * @param rules List of rules in format ruleCode:parameter1,parameter2...
	 * @param customMessages custom error messages, respect to order of the rules
	 * @param errorHandler Object that will be responsible for handling and outputing errors
	 * @return Validator object
	 * @throws ValidatorInputNotResolved
	 * @throws RuleNotExists
	 */
	public Validator add(Object toValidate, String fieldName, String[] rules, String[] customMessages,
			ErrorHandler errorHandler) throws ValidatorInputNotResolved, RuleNotExists {
		ValidatorInputInterface input = resolveInputForClass(toValidate.getClass());
		input.setObjectOfValidation(toValidate);
		input.setInputName(fieldName);
		input.addErrorHandler(errorHandler);

		int i = 0;
		for (String ruleStr : rules) {
			String m = "";
			if (customMessages != null && customMessages.length > i)
				m = customMessages[i++];
			ValidationRule rule = ValidationRuleFactory.getValidationRule(ruleStr);
			if (rule.canApplyToObject(input.getValueToValidate()))
				input.addRule(rule, m);
		}
		this.add(input);
		return this;
	}

	
	/**
	 * 
	 * @param input Input that will be validated
	 * @return
	 */
	public Validator add(ValidatorInputInterface input) {
		this.inputFields.add(input);
		input.setOnChangeValidate(dynamicValidation);
		input.addErrorHandler(formErrorHandler);
		return this;
	}
	
	private boolean dynamicValidation = false;

	/**
	 * 
	 * @param activate indicate when validation is dynamic or not
	 * @return instance of Validator
	 */
	public Validator dynamicallyValidate(boolean activate) {
		dynamicValidation = activate;
		for (ValidatorInputInterface validatorInputInterface : inputFields) {
			validatorInputInterface.setOnChangeValidate(activate);
		}
		return this;
	}

	
	/**
	 * Validate all objects
	 * @return instance of Validator
	 */
	public Validator validateAll() {
		valid = true;
		for (ValidatorInputInterface input : inputFields) {
			valid &= input.validate().isValid();
		}
		return this;
	}

	/**
	 * 
	 * @return true if validation is passed, otherwise false
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * 
	 * @return List of all fields
	 */
	public List<ValidatorInputInterface> getFields() {
		return this.inputFields;
	}

	
	/**
	 * 
	 * @return List of all invalid field
	 */
	public List<ValidatorInputInterface> getInvalidFields() {
		List<ValidatorInputInterface> invalidFields = new LinkedList<>();
		for (ValidatorInputInterface field : inputFields) {
			if (!field.isValid())
				invalidFields.add(field);
		}
		return invalidFields;
	}

	
	/**
	 * 
	 * @param o input object
	 * @return ValidatorInputInterface that correspond to object o
	 */
	public ValidatorInputInterface getInput(Object o) {
		for (ValidatorInputInterface validatorInputInterface : inputFields) {
			if (validatorInputInterface.getObjectOfValidation().equals(o))
				return validatorInputInterface;
		}
		return null;
	}
	
	/**
	 * Force container to show all validation errors
	 * 
	 * @param handler
	 * @return Validator instance
	 */
	public Validator errorsContainer(Object container) throws ErrorHandlerNotResolved {
		try {
			Constructor<?> cons;
			cons = defaultFormErrorHandler.getConstructor(container.getClass());
			ErrorHandler errorHandler = (ErrorHandler) cons.newInstance(container);
			return this.errorsContainer(errorHandler);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ErrorHandlerNotResolved(defaultFormErrorHandler, container);
		}
	}
	
	
	/**
	 * Force handler to show all validation errors 
	 * 
	 * @param handler
	 * @return Validator instance
	 */
	public Validator errorsContainer(ErrorHandler handler){
		formErrorHandler = handler;
		this.updateFormErrorHandler();
		return this;
	}
	
	/**
	 * Change default class that will be used for resolving input error handler
	 * @param errorHandlerClass new handler class
	 * @return Validator
	 */
	public Validator setDefaultInputErrorHandler(Class errorHandlerClass){
		this.defaultInputErrorHandler = errorHandlerClass;
		return this;
	}

	private void updateFormErrorHandler()
	{
		for (ValidatorInputInterface input : inputFields) {
			input.addErrorHandler(formErrorHandler);
		}
	}
	private ValidatorInputInterface resolveInputForClass(Class realInputClass) throws ValidatorInputNotResolved {
			if (registerdInputsOnInstance.containsKey(realInputClass))
				return resolveInputForClass(realInputClass, registerdInputsOnInstance);
			return resolveInputForClass(realInputClass, registerdInputs);
	}

	private ValidatorInputInterface resolveInputForClass(Class realInputClass, HashMap<Class, Class> inputs) throws ValidatorInputNotResolved
			 {
		Constructor<?> cons;
		try {
			cons = inputs.get(realInputClass).getConstructor();
			ValidatorInputInterface inputObject = (ValidatorInputInterface) cons.newInstance();
			return inputObject;
		} catch (NullPointerException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ValidatorInputNotResolved();
		}
		
	}
	
}
