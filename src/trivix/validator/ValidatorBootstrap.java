package trivix.validator;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import trivix.validator.dataValidation.PrimitiveValidatorInput;
import trivix.validator.dataValidation.interfaces.ValidatorInputInterface;
import trivix.validator.inputs.CheckBoxInput;
import trivix.validator.inputs.TextInput;
import trivix.validator.inputs.TextareaInput;
import trivix.validator.rules.ConfirmedRule;
import trivix.validator.rules.EmailRule;
import trivix.validator.rules.InRule;
import trivix.validator.rules.NumberRule;
import trivix.validator.rules.RequiredRule;
import trivix.validator.rules.SizeRule;

public class ValidatorBootstrap {
	
	
	public static void boot(){
		Validator.registerRule(new SizeRule());
		Validator.registerRule(new EmailRule());
		Validator.registerRule(new RequiredRule());
		Validator.registerRule(new InRule());
		Validator.registerRule(new ConfirmedRule());
		Validator.registerRule(new NumberRule());
		
		Validator.registerInputClass(String.class, PrimitiveValidatorInput.class);
		Validator.registerInputClass(Integer.class, PrimitiveValidatorInput.class);		
		Validator.registerInputClass(Double.class, PrimitiveValidatorInput.class);		
		Validator.registerInputClass(JTextField.class, TextInput.class);
		Validator.registerInputClass(JTextArea.class, TextareaInput.class);
		Validator.registerInputClass(JCheckBox.class, CheckBoxInput.class);
	}
}
