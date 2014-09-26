package nl.reveance.jgtk.exception;

public class HierarchyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HierarchyException() {
		super();
	}

	public HierarchyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HierarchyException(String message, Throwable cause) {
		super(message, cause);
	}

	public HierarchyException(String message) {
		super(message);
	}

	public HierarchyException(Throwable cause) {
		super(cause);
	}

}
