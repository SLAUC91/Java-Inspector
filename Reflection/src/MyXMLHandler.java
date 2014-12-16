import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class MyXMLHandler {
	
	public static void writeXML(Document doc, OutputStream streamToWrite) throws Exception
	{
		XMLOutputter fmt = new XMLOutputter();
		fmt.output(doc, streamToWrite);
	}
	public static void writeXML(Document doc, Writer writer) throws Exception
	{
		XMLOutputter fmt = new XMLOutputter();
		fmt.output(doc, writer);
	}
	
	public static Document readFromFile(String fileName) throws Exception
	{
		SAXBuilder builder = new SAXBuilder();
		return builder.build(new File(fileName));
	}
}
