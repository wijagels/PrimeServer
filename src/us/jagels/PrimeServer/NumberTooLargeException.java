package us.jagels.PrimeServer;

public class NumberTooLargeException extends Exception {

	private static final long serialVersionUID = 5871918494002381408L;

	public NumberTooLargeException(String message) {
		super(message);
	}

}
