import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import document.ArchieveCollection;
import freemarker.template.TemplateException;
import template.TemplateBuilder;

public class Main {

	private static final String HtmlOutputPath = "../docs/pages";
	private static final String TemplateInputPath = "../templates";
	private static final String DataInputPath = "../documents";
	
	private static final File DataInputRoot = new File(DataInputPath);
	private static final File OutputRoot = new File(HtmlOutputPath);
	
	private static final File OutputCollectionRoot = new File(HtmlOutputPath, "collections");
	
	
	public static void main(String[] args) throws JAXBException, IOException, TemplateException {
		FileUtils.cleanDirectory(OutputRoot);
		
		Archieve archieve = new Archieve(DataInputRoot);
		
		OutputCollectionRoot.mkdir();
		for(ArchieveCollection collection : archieve.getCollections()) {
			File outputCollection = new File(OutputCollectionRoot, collection.name + ".html");
			if(outputCollection.exists()) {
				throw new RuntimeException();
			}
			
			HashMap<String, Object> arguments = new HashMap<>();
			arguments.put("laws", collection.laws);
			
			File templateCollection = new File(TemplateInputPath, "collection.template.html");
			String content = TemplateBuilder.generate(templateCollection, arguments);
			
			FileUtils.write(outputCollection, content, "utf-8");
		}
		
		File templateIndex = new File(TemplateInputPath, "index.template.html");
		HashMap<String, Object> vo = new HashMap<>();
		vo.put("bases", archieve.getBaseNodes());
		String content = TemplateBuilder.generate(templateIndex , vo);
		
		File outputIndex = new File(OutputRoot, "index.html");
		FileUtils.write(outputIndex, content, "utf-8");
	}

}
