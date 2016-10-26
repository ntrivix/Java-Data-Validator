package trivix.validator.inputs;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import trivix.validator.dataValidation.AbstractValidatorInput;

public class TextareaInput extends AbstractValidatorInput<JTextArea, String> {

	@Override
	public String getValueToValidate() {
		return getObjectOfValidation().getText();
	}

	@Override
	public Class<String> getValueType() {
		// TODO Auto-generated method stub
		return (Class<String>) getValueToValidate().getClass();
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
				TextareaInput self = this;
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
