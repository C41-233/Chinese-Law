package base;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.JAXBException;

import document.*;
import org.apache.commons.io.FileUtils;

import model.ArchieveException;
import model.Document;

public class Archive {

	private List<ArchiveRoot> rootNodes = new ArrayList<>();
	
	private List<Law> laws = new ArrayList<>();
	private HashSet<String> lawNames = new HashSet<>();
	
	public Archive(File root) throws IOException, JAXBException {
		for(File file : root.listFiles()) {
			ArchiveRoot node = (ArchiveRoot) createArchiveNode(null, file);
			rootNodes.add(node);
			System.out.println(node);
		}
		
		for (ArchiveRoot archiveRoot : rootNodes) {
			archiveRoot.setRoots(rootNodes);
		}

		//检查deprecated
		for(Law law : laws) {
			String document = law.getDeprecatedReplaceDocument();
			if(document != null && !lawNames.contains(document)) {
				throw new ArchieveException("《%s》的替代文件《%s》不存在", law.name, document);
			}
		}
	}

	public List<Law> getLaws(){
		return laws;
	}

	public List<ArchiveRoot> getRoots() {
		return rootNodes;
	}
	
	private ArchiveNode createArchiveNode(ArchiveNode parent, File file) throws IOException, JAXBException{
		String name = file.getName();
		ArchiveNode node = parent != null ? new ArchiveNode(parent, name) : new ArchiveRoot(name);
		
		for(File child : file.listFiles()) {
			if(isDocumentNode(child)) {
				node.collections.add(createArchiveCollection(node, child));
			}
			else {
				node.nodes.add(createArchiveNode(node, child));
			}
		}
		node.collections.sort(new NameComparator<>(ArchiveCollection::getName));
		node.nodes.sort(new NameComparator<>(ArchiveNode::getName));
		return node;
	}
	
	private ArchiveCollection createArchiveCollection(ArchiveNode parent, File file) throws IOException, JAXBException {
		String collectionName = file.getName();
		ArchiveCollection collection = new ArchiveCollection(parent, collectionName);
		for(File documentFile : file.listFiles()) {
			String content = FileUtils.readFileToString(documentFile, "utf-8");
			String filename = documentFile.getName();
			if(!filename.endsWith(".xml")) {
				throw new ArchieveException("%s 文件名不符合规范", filename);
			}
			String name = filename.substring(0, filename.length() - 4);
			Law law = new Law(collection, name);
			if(law.name.contains("(") || law.name.contains(")")
					|| law.name.contains(" ")
					|| law.name.contains("《") || law.name.contains("》")
			) {
				throw new ArchieveException("法律名称非法字符：%s", law.name);
			}
			if(content.length() > 0) {
				Document document = DocumentReader.read(documentFile);
				law.setDocument(document);
			}
			laws.add(law);
			lawNames.add(law.name);
			collection.laws.add(law);
		}
		collection.laws.sort(new NameComparator<>(law -> law.name));
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
