package document;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.Amendment;
import model.ArchieveException;
import model.Document;
import model.Revision;

public class DocumentReader {

	private static final Unmarshaller unmarshaller;
	
	static {
		JAXBContext ctx;
		try {
			ctx = JAXBContext.newInstance(Revision.class, Amendment.class, Document.class);
			unmarshaller = ctx.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Document read(File file) throws JAXBException {
		try {
			Document document = (Document) unmarshaller.unmarshal(file);
			document.valid();
			return document;
		}
		catch (ArchieveException e) {
			throw new ArchieveException(e, "解析%s出现错误", file.getAbsolutePath());
		}
	}	
	
}
