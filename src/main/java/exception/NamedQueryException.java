package exception;

public class NamedQueryException extends Exception{
	private static final long serialVersionUID = 1L;

	public NamedQueryException(String message)
	{
		super(message);
	}

	public NamedQueryException(String message, Exception e) {
		super(message, e);
	}

}
