package exception;

public class TooManyPlayersException extends RuntimeException {

	static final long serialVersionUID = 0x5;
	
	public TooManyPlayersException()
	{
		super();
	}
	
	public TooManyPlayersException(String s)
	{
		super(s);
	}
}
