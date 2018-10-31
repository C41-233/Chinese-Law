import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import document.ArchieveCollection;
import document.ArchieveNode;
import document.DocumentReader;
import document.Law;
import model.Document;

public class Archieve {

	private List<ArchieveNode> baseNodes = new ArrayList<>();
	
	private List<Law> laws = new ArrayList<>();
	
	private List<ArchieveCollection> collections = new ArrayList<>();
	
	public Archieve(File root) throws IOException, JAXBException {
		for(File file : root.listFiles()) {
			ArchieveNode node = createArchieveNode(file);
			baseNodes.add(node);
			System.out.println(node);
		}
	}

	public List<ArchieveNode> getBaseNodes(){
		return baseNodes;
	}
	
	public List<Law> getLaws(){
		return laws;
	}
	
	public List<ArchieveCollection> getCollections(){
		return collections;
	}
	
	private ArchieveNode createArchieveNode(File file) throws IOException, JAXBException{
		ArchieveNode node = new ArchieveNode();
		node.name = file.getName();
		
		for(File child : file.listFiles()) {
			if(isDocumentNode(child)) {
				node.documents.add(createArchieveCollection(child));
			}
			else {
				node.childs.add(createArchieveNode(child));
			}
		}
		node.documents.sort((d1, d2) -> d1.name.compareTo(d2.name));
		node.childs.sort((c1, c2) -> c1.name.compareTo(c2.name));
		return node;
	}
	
	private ArchieveCollection createArchieveCollection(File file) throws IOException, JAXBException {
		ArchieveCollection collection = new ArchieveCollection();
		collection.name = file.getName();
		for(File documentFile : file.listFiles()) {
			String content = FileUtils.readFileToString(documentFile, "utf-8");
			Law law = new Law();
			String filename = documentFile.getName();
			law.name = filename.substring(0, filename.length() - 4);
			if(content.length() > 0) {
				Document document = DocumentReader.read(documentFile);
				law.setDocument(document);
				laws.add(law);
			}
			laws.add(law);
			collection.laws.add(law);
		}
		collections.add(collection);
		return collection;
	}

	private static boolean isDocumentNode(File dir) {
		File[] files = dir.listFiles();
		if(files.length == 0) {
			return true;
		}
		int fc = 0, ff = 0;
		for (File file : files) {
			if(file.isDirectory()) {
				ff++;
			}
			else {
				fc++;
			}
		}
		if(fc == 0) {
			return false;
		}
		if(ff == 0) {
			return true;
		}
		throw new RuntimeException();
	}
	
}
