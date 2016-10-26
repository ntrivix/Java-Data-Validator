package trivix.validator.inputs;

import javax.swing.JCheckBox;

import trivix.validator.dataValidation.AbstractValidatorInput;

public class CheckBoxInput extends AbstractValidatorInput<JCheckBox, Boolean> {

	@Override
	public Boolean getValueToValidate() {
		return getObjectOfValidation().isSelected();
	}

	@Override
	public Class<Boolean> getValueType() {
		return Boolean.class;
	}

	@Override
	public void setOnChangeValidate(boolean onchange) {
		
	}

}
