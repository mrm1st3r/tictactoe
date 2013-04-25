package exception;

public class StrategyException extends RuntimeException {

	static final long serialVersionUID = 0x4;
	
	public StrategyException()
	{
		super();
	}
	
	public StrategyException(String msg)
	{
		super(msg);
	}
}
