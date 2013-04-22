package exception;

public class TooManyPlayersException extends RuntimeException {

	public TooManyPlayersException()
	{
		super();
	}
	
	public TooManyPlayersException(String s)
	{
		super(s);
	}
}
