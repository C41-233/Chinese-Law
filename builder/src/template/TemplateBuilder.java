package template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class TemplateBuilder {

	private static Configuration configuration;
	
	static{
		configuration = new Configuration(new Version("2.3.27"));
		configuration.setDefaultEncoding("utf8");
		configuration.setLogTemplateExceptions(false);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setNumberFormat("computer");
		
		DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_27);
		builder.setExposeFields(true);
		configuration.setObjectWrapper(builder.build());
	}
	
	public static String generate(File input, HashMap<String, Object> arguments) throws IOException, TemplateException {
		configuration.setDirectoryForTemplateLoading(input.getParentFile());
		
		Template template = configuration.getTemplate(input.getName());
		
		StringWriter writer = new StringWriter();
		template.process(arguments, writer);
		return writer.toString();
	}
	
}
