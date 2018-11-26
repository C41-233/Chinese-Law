package page;

import java.util.List;

import document.ArchieveCollection;
import document.ArchieveNode;

public interface IPage {

	public String getBaseDirectoryName();

	public String getTemplateCollectionName();
	
	public String getTemplateNodeName();
	
	public List<ArchieveCollection> getCollections();

	public List<ArchieveNode> getNodes();

	
}
