package trivix.validator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import trivix.validator.dataValidation.PrimitiveValidatorInput;
import trivix.validator.dataValidation.ValidationRuleFacade;
import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.errors.ValidationError;
import trivix.validator.dataValidation.exceptions.ValidatorInputNotResolved;
import trivix.validator.dataValidation.interfaces.ValidationRule;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;
import trivix.validator.dataValidation.interfaces.ValidatorInterface;

public class Validator implements ValidatorInterface {
	private ArrayList<ValidatorInputInterface> inputFields = new ArrayList<>();
	private boolean valid = false;

	private static boolean isBooted = false;
	private static HashMap<String, ValidationRule> avaiableRules = new HashMap<>();

	private static HashMap<Class, Class> registerdInputs = new HashMap<>();
	private static HashMap<Class, Class> registerdInputsOnInstance = new HashMap<>();

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
	 * @param realInputClass
	 * @param resolverClass
	 */
	public void registerInputClassOnInstance(Class realInputClass, Class resolverClass) {
		registerdInputsOnInstance.put(realInputClass, resolverClass);
	}

	/**
	 * Register new validation rule
	 * 
	 * @param rule
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
	
	public static InputErrors[] validate(Object object, String[] rules) throws ValidatorInputNotResolved{
		return Validator.validate().add(object, Object.class.getName(), rules).validateAll().errors();
	}
	
	

	@Override
	public InputErrors[] errors() {
		List<InputErrors> e = new LinkedList<>();
		for (ValidatorInputInterface input : inputFields) {
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

	public Validator add(Object toValidate, String fieldName, String[] rules) throws ValidatorInputNotResolved {
		return this.add(toValidate, fieldName, rules, null);
	}

	public Validator add(Object toValidate, String fieldName, String[] rules, String[] customMessages) throws ValidatorInputNotResolved {
		ValidatorInputInterface input = resolveInputForClass(toValidate.getClass());
		input.setObjectOfValidation(toValidate);
		input.setInputName(fieldName);

		int i = 0;
		for (String ruleStr : rules) {
			String m = "";
			if (customMessages != null && customMessages.length > i)
				m = customMessages[i++];
			ValidationRule rule = ValidationRuleFacade.getValidator(ruleStr);
			if (rule.canApplyToObject(input.getValueToValidate()))
				input.addRule(rule, m);
		}
		this.add(input);
		return this;
	}

	private boolean dynamicValidation = false;
	public Validator dynamicallyValidate(boolean activate){
		dynamicValidation = activate;
		for (ValidatorInputInterface validatorInputInterface : inputFields) {
			validatorInputInterface.setOnChangeValidate(activate);
		}
		return this;
	}
	
	public Validator add(ValidatorInputInterface input) {
		this.inputFields.add(input);
		input.setOnChangeValidate(dynamicValidation);
		return this;
	}

	@Override
	public ValidatorInterface validateAll() {
		valid = true;
		for (ValidatorInputInterface input : inputFields) {
			valid &= input.validate().isValid();
		}
		return this;
	}

	@Override
	public boolean isValid() {
		return valid;
	}

	@Override
	public List<ValidatorInputInterface> getFields() {
		return this.inputFields;
	}

	@Override
	public List<ValidatorInputInterface> getInvalidFields() {
		List<ValidatorInputInterface> invalidFields = new LinkedList<>();
		for (ValidatorInputInterface field : inputFields) {
			if (!field.isValid())
				invalidFields.add(field);
		}
		return invalidFields;
	}

	public ValidatorInputInterface getInput(Object o) {
		for (ValidatorInputInterface validatorInputInterface : inputFields) {
			if (validatorInputInterface.getObjectOfValidation().equals(o))
				return validatorInputInterface;
		}
		return null;
	}

	private ValidatorInputInterface resolveInputForClass(Class realInputClass) throws ValidatorInputNotResolved {
		try {
			if (registerdInputsOnInstance.containsKey(realInputClass))
				return resolveInputForClass(realInputClass, registerdInputsOnInstance);
			return resolveInputForClass(realInputClass, registerdInputs);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new ValidatorInputNotResolved();
		}
	}

	private ValidatorInputInterface resolveInputForClass(Class realInputClass, HashMap<Class, Class> inputs)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<?> cons = inputs.get(realInputClass).getConstructor();
		ValidatorInputInterface inputObject = (ValidatorInputInterface) cons.newInstance();
		return inputObject;
	}
}
