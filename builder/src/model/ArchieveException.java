package model;
public class ArchieveException extends RuntimeException{

	public ArchieveException(String format, Object...args) {
		this(null, format, args);
	}

	public ArchieveException(Throwable cause, String format, Object...args) {
		super(args == null || args.length == 0 ? format : String.format(format, args), cause);
	}
	
}
