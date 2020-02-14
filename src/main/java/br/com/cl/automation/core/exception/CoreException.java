package br.com.cl.automation.core.exception;

public class CoreException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CoreException(String string) {
		super(string);
	}

	public CoreException(Throwable e) {
		super(e);
	}
}
