package XML_reader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XML_reader {

    File file;

    public XML_reader(File file) {
        this.file = file;
    }

    public NodeList readByTagName(String tag){
        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //Build Document
        Document document = null;
        try {
            document = builder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();


        NodeList nList = document.getElementsByTagName(tag);

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
        }
        return nList;
    }
}
