package trivix.validator.dataValidation.exceptions;

public class ErrorHandlerNotResolved  extends Exception {

	private Class handlerClass;
	private Object obj;
	public ErrorHandlerNotResolved(Class handlerClass, Object obj) {
		this.handlerClass=handlerClass;
		this.obj = obj;
	}
	@Override
	public String getMessage() {
		return "Error handler "+handlerClass+" can't handle object of "+obj.getClass()+" type";
	}
	
	
}
