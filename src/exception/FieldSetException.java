package exception;

public class FieldSetException extends RuntimeException {

	static final long serialVersionUID = 0x2;
	
	public FieldSetException()
	{
		super();
	}
	
	public FieldSetException(String msg)
	{
		super(msg);
	}
}