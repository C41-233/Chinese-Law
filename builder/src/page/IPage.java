package page;

import java.util.List;

import document.ArchiveCollection;
import document.ArchiveNode;

public interface IPage {

	public String getBaseDirectoryName();

	public String getTemplateCollectionName();
	
	public String getTemplateNodeName();
	
	public List<ArchiveCollection> getCollections();

	public List<ArchiveNode> getNodes();

	
}
