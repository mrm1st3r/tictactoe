package exception;

public class StrategyNotSetException extends RuntimeException {

	public StrategyNotSetException()
	{
		super();
	}
	
	public StrategyNotSetException(String msg)
	{
		super(msg);
	}
}
