package page;

import java.util.ArrayList;
import java.util.List;

import document.ArchieveCollection;
import document.ArchieveNode;
import document.ArchieveRoot;
import document.IArchieveNode;
import document.Law;
import model.ArchieveException;

public class PageLocal implements IPage{

	private List<ArchieveCollection> collections = new ArrayList<>();
	private List<ArchieveNode> nodes = new ArrayList<>();
	
	private List<ArchieveRoot> roots = new ArrayList<>();

	public PageLocal(List<Law> laws) {
		for(Law law : laws) {
			if(law.isLocal()) {
				createCollection(law);
			}
		}
		
		for(ArchieveRoot root : roots) {
			root.setRoots(roots);
		}
	}

	private void createCollection(Law law) {
		List<String> paths = law.getCategoryPath();
		if(paths == null) {
			throw new ArchieveException("地方法规必须包含地方路径：%s", law.name);
		}
		ArchieveRoot root = getOrCreateRoot(paths.get(0)); //省

		if(paths.size() <= 1) {
			ArchieveNode node = getOrCreateNode(root, root.getName());
			fillNodes(node, law);
			return;
		}
	}
	
	private void fillNodes(ArchieveNode root, Law law) {
		String name = law.getParent().getName();
		List<String> nodes = new ArrayList<>();
		IArchieveNode node = law.getParent().getParent();
		while(node != null) {
			nodes.add(node.getName());
			node = node.getParent();
		}
		
		ArchieveNode theNode = root;
		for(int i = nodes.size()-1; i>=0; i--) {
			String path = nodes.get(i);
			theNode = getOrCreateNode(theNode, path);
		}
		
		ArchieveCollection collection = getOrCreateCollection(theNode, name);
		collection.laws.add(law);
	}
	
	private ArchieveRoot getOrCreateRoot(String name) {
		for(ArchieveRoot root : roots) {
			if(root.getName().equals(name)) {
				return root;
			}
		}
		ArchieveRoot root = new ArchieveRoot(name);
		roots.add(root);
		nodes.add(root);
		return root;
	}

	private ArchieveNode getOrCreateNode(ArchieveNode node, String name) {
		for(ArchieveNode child : node.nodes) {
			if(child.getName().equals(name)) {
				return child;
			}
		}
		ArchieveNode child = new ArchieveNode(node, name);
		node.nodes.add(child);
		nodes.add(child);
		return child;
	}

	private ArchieveCollection getOrCreateCollection(ArchieveNode node, String name) {
		for(ArchieveCollection child : node.collections) {
			if(child.getName().equals(name)) {
				return child;
			}
		}
		ArchieveCollection child = new ArchieveCollection(node, name);
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
	public List<ArchieveCollection> getCollections() {
		return collections;
	}

	@Override
	public List<ArchieveNode> getNodes() {
		return nodes;
	}

	public List<ArchieveRoot> getRoots() {
		return roots;
	}

}
