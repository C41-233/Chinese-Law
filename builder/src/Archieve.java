import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.tools.Diagnostic;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import document.ArchieveCollection;
import document.ArchieveNode;
import document.DocumentReader;
import document.Law;
import model.ArchieveException;
import model.Document;

public class Archieve {

	private List<ArchieveNode> baseNodes = new ArrayList<>();
	
	private List<Law> laws = new ArrayList<>();
	private HashSet<String> lawNames = new HashSet<>();
	
	private List<ArchieveCollection> collections = new ArrayList<>();
	
	public Archieve(File root) throws IOException, JAXBException {
		for(File file : root.listFiles()) {
			ArchieveNode node = createArchieveNode(null, file);
			baseNodes.add(node);
			System.out.println(node);
		}
		
		//检查deprecated
		for(Law law : laws) {
			String document = law.getDeprecatedReplaceDocument();
			if(document != null && !lawNames.contains(document)) {
				throw new ArchieveException("《%s》的替代文件《%s》不存在", law.name, document);
			}
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
	
	private ArchieveNode createArchieveNode(ArchieveNode parent, File file) throws IOException, JAXBException{
		String name = file.getName();
		ArchieveNode node = new ArchieveNode(parent, name);
		
		for(File child : file.listFiles()) {
			if(isDocumentNode(child)) {
				node.documents.add(createArchieveCollection(node, child));
			}
			else {
				node.childs.add(createArchieveNode(node, child));
			}
		}
		node.documents.sort((d1, d2) -> d1.name.compareTo(d2.name));
		node.childs.sort((c1, c2) -> c1.name.compareTo(c2.name));
		return node;
	}
	
	private ArchieveCollection createArchieveCollection(ArchieveNode parent, File file) throws IOException, JAXBException {
		String name = file.getName();
		ArchieveCollection collection = new ArchieveCollection(parent, name);
		for(File documentFile : file.listFiles()) {
			String content = FileUtils.readFileToString(documentFile, "utf-8");
			Law law = new Law();
			String filename = documentFile.getName();
			law.name = filename.substring(0, filename.length() - 4);
			if(law.name.contains("(") || law.name.contains(")")
					|| law.name.contains(" ")
					|| law.name.contains("《") || law.name.contains("》")
			) {
				throw new ArchieveException("法律名称非法字符：%s", law.name);
			}
			if(content.length() > 0) {
				Document document = DocumentReader.read(documentFile);
				law.setDocument(document);
				laws.add(law);
			}
			laws.add(law);
			lawNames.add(law.name);
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
