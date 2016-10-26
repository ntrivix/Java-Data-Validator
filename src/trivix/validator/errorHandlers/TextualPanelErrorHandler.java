package trivix.validator.errorHandlers;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import trivix.validator.dataValidation.errors.InputErrors;
import trivix.validator.dataValidation.errors.handler.AbstractErrorHandler;

public class TextualPanelErrorHandler extends AbstractErrorHandler<JPanel> {

	private JLabel indicator = new JLabel("");
	private static final ImageIcon wrong;
	private static final ImageIcon right;
	
	static {
		wrong = new ImageIcon(SimplePanelErrorHandler.class.getClassLoader().getResource("trivix/validator/resources/images/Error-20.png"));
		right = new ImageIcon(SimplePanelErrorHandler.class.getClassLoader().getResource("trivix/validator/resources/images/Ok-20.png"));
	}
	public TextualPanelErrorHandler(JPanel output) {
		super(output);
		indicator.setOpaque(true);
		getTargetOutputObject().add(indicator);
	}

	@Override
	public void successState() {
		indicator.setText("");
	}

	@Override
	public void errorState() {
		indicator.setText("<html>"+this.toString()+"</html>");
	}

	@Override
	public void cleanState() {
		indicator.setText("");
	}
	
	@Override
	public String toString() {
		StringBuilder e = new StringBuilder();
		for (InputErrors inputErrors : this.getErrors()) {
			e.append("<p style=\"margin-top:2px;margin-bottom:2px;padding:5px;color:white;background-color:red;\">"+inputErrors.toString().replaceAll("\n", "<br>")+"</p>");
		}
		return e.toString();
	}

}