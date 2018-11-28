import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import base.Archive;
import base.Config;
import document.ArchiveCollection;
import document.ArchiveNode;
import freemarker.template.TemplateException;
import model.ArchieveException;
import page.IPage;
import page.PageLocal;
import page.PageNational;
import template.TemplateBuilder;

public class Main {

	public static void main(String[] args) throws JAXBException, IOException, TemplateException {
		FileUtils.cleanDirectory(Config.OutputRoot);
		Archive archive = new Archive(Config.DataInputRoot);

		PageNational pageNational = new PageNational(archive.getRoots());
		outputPage(pageNational);
		
		PageLocal pageLocal = new PageLocal(archive.getLaws());
		outputPage(pageLocal);

		File templateIndex = new File(Config.TemplateInputPath, "index.template.html");
		HashMap<String, Object> vo = new HashMap<>();
		vo.put("nationals", pageNational);
		vo.put("locals", pageLocal);
		
		String content = TemplateBuilder.generate(templateIndex , vo);
		File outputIndex = new File(Config.OutputRoot, "index.html");
		FileUtils.write(outputIndex, content, "utf-8");
	}

	private static void outputPage(IPage page) throws IOException, TemplateException {
		File outputRoot = new File(Config.HtmlOutputPath, page.getBaseDirectoryName());
		outputRoot.mkdir();
		
		for(ArchiveCollection collection : page.getCollections()) {
			File outputCollection = new File(outputRoot, collection.getFilename() + ".html");
			if(outputCollection.exists()) {
				throw new ArchieveException("名字重复：%s", collection.getName());
			}
			
			HashMap<String, Object> arguments = new HashMap<>();
			arguments.put("page", collection);
			arguments.put("data", page.getBaseDirectoryName());
			
			File templateCollection = new File(Config.TemplateInputPath, page.getTemplateCollectionName());
			String content = TemplateBuilder.generate(templateCollection, arguments);
			
			FileUtils.write(outputCollection, content, "utf-8");
		}
		
		for(ArchiveNode node : page.getNodes()) {
			File outputNode = new File(outputRoot, node.getFilename() + ".html");
			if(outputNode.exists()) {
				throw new ArchieveException("名字重复：%s", node.getName());
			}
			
			HashMap<String, Object> arguments = new HashMap<>();
			arguments.put("page", node);
			arguments.put("data", page.getBaseDirectoryName());
			
			File templateNode = new File(Config.TemplateInputPath, page.getTemplateNodeName());
			String content = TemplateBuilder.generate(templateNode, arguments);
			
			FileUtils.write(outputNode, content, "utf-8");
		}
	}
	
}
