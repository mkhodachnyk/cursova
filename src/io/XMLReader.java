package io;

import surface.impl.Surface;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;

import javax.swing.JTextField;
import javax.xml.parsers.*;
import java.awt.event.*;

public class XMLReader {
    private InputSource XMLsource;
    private SAXParser parser;
    private JTextField myTextXFormula, myTextYFormula, myTextZFormula;
    private JTextField myTextStartU, myTextEndU, myTextStartT, myTextEndT;
    private JTextField myTextCountU, myTextCountT;

    public XMLReader(InputStream inputStream, JTextField XFormula, JTextField YFormula, JTextField ZFormula,
                     JTextField startU, JTextField endU, JTextField startT, JTextField endT,
                     JTextField countU, JTextField countT) {
        myTextXFormula = XFormula;
        myTextYFormula = YFormula;
        myTextZFormula = ZFormula;
        myTextStartU = startU;
        myTextEndU = endU;
        myTextStartT = startT;
        myTextEndT = endT;
        myTextCountU = countU;
        myTextCountT = countT;

        try {
            Reader reader = new InputStreamReader(inputStream);
            XMLsource = new InputSource(reader);
            parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(XMLsource, new MyHandler());
        } catch (ParserConfigurationException e) {
            System.out.println("error parse");
        } catch (SAXException e) {
            System.out.println("error SAX");
        } catch (IOException e) {
            System.out.println("error IO");
        }
    }

    private class MyHandler extends DefaultHandler {
        public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attrs) {
            if (qualifiedName.equals("formula")) {
                myTextXFormula.setText(attrs.getValue("x"));
                myTextYFormula.setText(attrs.getValue("y"));
                myTextZFormula.setText(attrs.getValue("z"));
            } else if (qualifiedName.equals("boundU")) {
                myTextStartU.setText(attrs.getValue("start"));
                myTextEndU.setText(attrs.getValue("end"));
            } else if (qualifiedName.equals("boundT")) {
                myTextStartT.setText(attrs.getValue("start"));
                myTextEndT.setText(attrs.getValue("end"));
            } else if (qualifiedName.equals("count")) {
                myTextCountU.setText(attrs.getValue("u"));
                myTextCountT.setText(attrs.getValue("t"));
            }
        }

        public void endElement(String namespaceURI, String localName, String qualifiedName) {
        }
    }



}
