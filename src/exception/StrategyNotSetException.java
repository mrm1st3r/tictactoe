package exception;

public class StrategyNotSetException extends RuntimeException {

	static final long serialVersionUID = 0x4;
	
	public StrategyNotSetException()
	{
		super();
	}
	
	public StrategyNotSetException(String msg)
	{
		super(msg);
	}
}
