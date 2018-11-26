package base;

import java.io.File;

public final class Config {

	public static final String HtmlOutputPath = "../docs/pages";
	public static final String TemplateInputPath = "../templates";
	public static final String DataInputPath = "../documents";
	
	public static final File DataInputRoot = new File(DataInputPath);
	public static final File OutputRoot = new File(HtmlOutputPath);
	
}
