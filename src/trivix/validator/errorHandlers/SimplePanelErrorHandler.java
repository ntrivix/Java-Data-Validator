package trivix.validator.errorHandlers;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import trivix.validator.dataValidation.errors.handler.AbstractErrorHandler;

public class SimplePanelErrorHandler extends AbstractErrorHandler<JPanel> {

	private JLabel indicator = new JLabel("");
	private static final ImageIcon wrong;
	private static final ImageIcon right;
	
	static {
		wrong = new ImageIcon(SimplePanelErrorHandler.class.getClassLoader().getResource("trivix/validator/resources/images/Error-20.png"));
		right = new ImageIcon(SimplePanelErrorHandler.class.getClassLoader().getResource("trivix/validator/resources/images/Ok-20.png"));
	}
	public SimplePanelErrorHandler(JPanel output) {
		super(output);
		getTargetOutputObject().add(indicator);
	}

	@Override
	public void successState() {
		indicator.setIcon(right);
	}

	@Override
	public void errorState() {
		indicator.setIcon(wrong);
	}

	@Override
	public void cleanState() {
		indicator.setText("");
	}

}
