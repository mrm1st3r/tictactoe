package exception;

public class PlayerException extends RuntimeException {

	static final long serialVersionUID = 0x5;
	
	public PlayerException()
	{
		super();
	}
	
	public PlayerException(String s)
	{
		super(s);
	}
}
