package page;

import java.util.ArrayList;
import java.util.List;

import document.ArchiveCollection;
import document.ArchiveNode;
import document.ArchiveRoot;
import document.IArchieveNode;
import document.Law;
import model.ArchieveException;

public class PageLocal implements IPage{

	private List<ArchiveCollection> collections = new ArrayList<>();
	private List<ArchiveNode> nodes = new ArrayList<>();
	
	private List<ArchiveRoot> roots = new ArrayList<>();

	public PageLocal(List<Law> laws) {
		for(Law law : laws) {
			if(law.isLocal()) {
				createCollection(law);
			}
		}
		
		for(ArchiveRoot root : roots) {
			root.setRoots(roots);
		}
	}

	private void createCollection(Law law) {
		List<String> paths = law.getCategoryPath();
		if(paths == null) {
			throw new ArchieveException("�ط������������ط�·����%s", law.name);
		}
		ArchiveRoot root = getOrCreateRoot(paths.get(0)); //ʡ

		if(paths.size() <= 1) {
			ArchiveNode node = getOrCreateNode(root, root.getName());
			fillNodes(node, law);
			return;
		}
	}
	
	private void fillNodes(ArchiveNode root, Law law) {
		String name = law.getParent().getName();
		List<String> nodes = new ArrayList<>();
		IArchieveNode node = law.getParent().getParent();
		while(node != null) {
			nodes.add(node.getName());
			node = node.getParent();
		}
		
		ArchiveNode theNode = root;
		for(int i = nodes.size()-1; i>=0; i--) {
			String path = nodes.get(i);
			theNode = getOrCreateNode(theNode, path);
		}
		
		ArchiveCollection collection = getOrCreateCollection(theNode, name);
		collection.laws.add(law);
	}
	
	private ArchiveRoot getOrCreateRoot(String name) {
		for(ArchiveRoot root : roots) {
			if(root.getName().equals(name)) {
				return root;
			}
		}
		ArchiveRoot root = new ArchiveRoot(name);
		roots.add(root);
		nodes.add(root);
		return root;
	}

	private ArchiveNode getOrCreateNode(ArchiveNode node, String name) {
		for(ArchiveNode child : node.nodes) {
			if(child.getName().equals(name)) {
				return child;
			}
		}
		ArchiveNode child = new ArchiveNode(node, name);
		node.nodes.add(child);
		nodes.add(child);
		return child;
	}

	private ArchiveCollection getOrCreateCollection(ArchiveNode node, String name) {
		for(ArchiveCollection child : node.collections) {
			if(child.getName().equals(name)) {
				return child;
			}
		}
		ArchiveCollection child = new ArchiveCollection(node, name);
		node.collections.add(child);
		collections.add(child);
		return child;
	}

	@Override
	public String getBaseDirectoryName() {
		return "locals";
	}

	@Override
	public String getTemplateCollectionName() {
		return "collection.template.html";
	}

	@Override
	public String getTemplateNodeName() {
		return "node.template.html";
	}

	@Override
	public List<ArchiveCollection> getCollections() {
		return collections;
	}

	@Override
	public List<ArchiveNode> getNodes() {
		return nodes;
	}

	public List<ArchiveRoot> getRoots() {
		return roots;
	}

}
