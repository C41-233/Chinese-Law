package base;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import document.ArchieveCollection;
import document.ArchieveNode;
import document.ArchieveRoot;
import document.DocumentReader;
import document.Law;
import model.ArchieveException;
import model.Document;

public class Archieve {

	private List<ArchieveRoot> rootNodes = new ArrayList<>();
	
	private List<Law> laws = new ArrayList<>();
	private HashSet<String> lawNames = new HashSet<>();
	
	public Archieve(File root) throws IOException, JAXBException {
		for(File file : root.listFiles()) {
			ArchieveRoot node = (ArchieveRoot) createArchieveNode(null, file);
			rootNodes.add(node);
			System.out.println(node);
		}
		
		for (ArchieveRoot archieveRoot : rootNodes) {
			archieveRoot.setRoots(rootNodes);
		}
		
		//���deprecated
		for(Law law : laws) {
			String document = law.getDeprecatedReplaceDocument();
			if(document != null && !lawNames.contains(document)) {
				throw new ArchieveException("��%s��������ļ���%s��������", law.name, document);
			}
		}
	}

	public List<Law> getLaws(){
		return laws;
	}

	public List<ArchieveRoot> getRoots() {
		return rootNodes;
	}
	
	private ArchieveNode createArchieveNode(ArchieveNode parent, File file) throws IOException, JAXBException{
		String name = file.getName();
		ArchieveNode node = parent != null ? new ArchieveNode(parent, name) : new ArchieveRoot(name);
		
		for(File child : file.listFiles()) {
			if(isDocumentNode(child)) {
				node.collections.add(createArchieveCollection(node, child));
			}
			else {
				node.nodes.add(createArchieveNode(node, child));
			}
		}
		node.collections.sort(new NameComparator<ArchieveCollection>(d -> d.getName()));
		node.nodes.sort(new NameComparator<ArchieveNode>(d -> d.getName()));
		return node;
	}
	
	private ArchieveCollection createArchieveCollection(ArchieveNode parent, File file) throws IOException, JAXBException {
		String collectionName = file.getName();
		ArchieveCollection collection = new ArchieveCollection(parent, collectionName);
		for(File documentFile : file.listFiles()) {
			String content = FileUtils.readFileToString(documentFile, "utf-8");
			String filename = documentFile.getName();
			if(!filename.endsWith(".xml")) {
				throw new ArchieveException("%s �ļ��������Ϲ淶", filename);
			}
			String name = filename.substring(0, filename.length() - 4);
			Law law = new Law(collection, name);
			if(law.name.contains("(") || law.name.contains(")")
					|| law.name.contains(" ")
					|| law.name.contains("��") || law.name.contains("��")
			) {
				throw new ArchieveException("�������ƷǷ��ַ���%s", law.name);
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
		collection.laws.sort(new NameComparator<Law>(law -> law.name));
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
