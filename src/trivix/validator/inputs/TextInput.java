package trivix.validator.inputs;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import trivix.validator.dataValidation.AbstractValidatorInput;

public class TextInput extends AbstractValidatorInput<JTextField, String> {
	
	@Override
	public String getValueToValidate() {
		return objectOfValidation.getText();
	}

	@Override
	public Class<String> getValueType() {
		return String.class;
	}

	@Override
	protected void afterValidation() {
		super.afterValidation();
		if (!this.isValid()){
			Border border = BorderFactory.createLineBorder(Color.red);
            objectOfValidation.setBorder(border);
		}else {
			Border border = BorderFactory.createLineBorder(Color.gray);
			objectOfValidation.setBorder(border);
		}
	}

	
	private KeyAdapter keyEvent;
	private boolean onChangeValidate = false;
	
	@Override
	public void setOnChangeValidate(boolean onchange) {
		if (onchange && !onChangeValidate){
			onChangeValidate  = true;
			if (keyEvent == null){
				TextInput self = this;
				keyEvent = new KeyAdapter() {

					@Override
					public void keyReleased(KeyEvent e) {
						super.keyReleased(e);
						self.validate();
					}
					
				};
			}
			objectOfValidation.addKeyListener(keyEvent);
		}
		if (!onchange){
			objectOfValidation.removeKeyListener(keyEvent);
			onChangeValidate = false;
		}
		
	}
	
	

}
