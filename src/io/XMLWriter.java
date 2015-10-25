package io;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import surface.*;
import org.w3c.dom.*;

public class XMLWriter {
    private ISurface mySurface;

    private Document makeDoc() throws IOException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser = factory.newDocumentBuilder();
        Document doc = parser.newDocument();
        Node root = doc.createElement("surface");
        doc.appendChild(root);

        Element element = doc.createElement("formula");
        element.setAttribute("x", mySurface.getFormula().getXFormula());
        element.setAttribute("y", mySurface.getFormula().getYFormula());
        element.setAttribute("z", mySurface.getFormula().getZFormula());
        root.appendChild(element);

        element = doc.createElement("boundU");
        element.setAttribute("start", mySurface.getFormula().getStringStartU());
        element.setAttribute("end", mySurface.getFormula().getStringEndU());
        root.appendChild(element);

        element = doc.createElement("boundT");
        element.setAttribute("start", mySurface.getFormula().getStringStartT());
        element.setAttribute("end", mySurface.getFormula().getStringEndT());
        root.appendChild(element);

        element = doc.createElement("count");
        element.setAttribute("u", mySurface.getStringCountU());
        element.setAttribute("t", mySurface.getStringCountT());
        root.appendChild(element);

        return doc;
    }

    public void write(ISurface surface, OutputStream stream) {
        mySurface = surface;
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            Document document = this.makeDoc();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(stream);

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IOException e1) {
            System.out.println(e1.getMessage());
        } catch (TransformerConfigurationException e2) {
            System.out.println(e2.getMessage());
        } catch (TransformerException e3) {
            System.out.println(e3.getMessage());
        }
    }

}
