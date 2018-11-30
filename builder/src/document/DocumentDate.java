package document;

import model.ArchieveException;

public class DocumentDate {

	public final int year;
	public final int month;
	public final int day;
	
	public DocumentDate(String value) {
		String[] tokens = value.split("年|月|日");
		year = Integer.parseInt(tokens[0]);
		month = Integer.parseInt(tokens[1]);
		day = Integer.parseInt(tokens[2]);
	}

	public static void valid(String value){
		if(!value.matches("^[0-9]{4}年(1[0-2]|[1-9])月([1-3][0-9]|[1-9])日$")){
			throw new ArchieveException("日期不符合：%s", value);
		}
	}

}
