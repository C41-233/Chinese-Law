package document;

public class DocumentDate {

	public final int year;
	public final int month;
	public final int day;
	
	public DocumentDate(String value) {
		String[] tokens = value.split("Äê|ÔÂ|ÈÕ");
		year = Integer.parseInt(tokens[0]);
		month = Integer.parseInt(tokens[1]);
		day = Integer.parseInt(tokens[2]);
	}
	
}
