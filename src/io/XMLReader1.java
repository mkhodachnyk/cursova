package io;

import java.io.*;

import javax.swing.JTextField;
import javax.xml.parsers.*;

import formula.*;
import formula.implementation.*;
import surface.*;
import surface.impl.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class XMLReader1 {
    private InputSource XMLsource;
    private SAXParser parser;
    private IFormula myFormula;
    String myTextXFormula = null, myTextYFormula = null, myTextZFormula = null;
    String myTextStartU = null, myTextEndU = null, myTextStartT = null, myTextEndT = null;
    String myTextCountU = null, myTextCountT = null;

    public XMLReader1(InputStream inputStream) {
        try {
            Reader reader = new InputStreamReader(inputStream);
            XMLsource = new InputSource(reader);
            parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(XMLsource, new MyHandler());
        } catch (ParserConfigurationException e) {
            System.out.println("error parse in XMLReader1");
        } catch (SAXException e) {
            System.out.println("error SAX in XMLReader1");
        } catch (IOException e) {
            System.out.println("error IO in XMLReader1");
        } catch (Exception e) {
//			e.printStackTrace();
            System.out.println("error EXCeption in XMLReader1");
        }
    }

    public IFormula getFormula() {

        return myFormula = new Formula(myTextXFormula, myTextYFormula, myTextZFormula,
                myTextStartT, myTextEndT, myTextStartU, myTextEndU);
    }

    public ISurface getSurface() {
        return new Surface(myFormula, myTextCountU, myTextCountT);
    }

    private class MyHandler extends DefaultHandler {
        public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attrs) {
            if (qualifiedName.equals("formula")) {
                myTextXFormula = attrs.getValue("x");
                myTextYFormula = attrs.getValue("y");
                myTextZFormula = attrs.getValue("z");
            } else if (qualifiedName.equals("boundU")) {
                myTextStartU = attrs.getValue("start");
                myTextEndU = attrs.getValue("end");
            } else if (qualifiedName.equals("boundT")) {
                myTextStartT = attrs.getValue("start");
                myTextEndT = attrs.getValue("end");
            } else if (qualifiedName.equals("count")) {
                myTextCountU = attrs.getValue("u");
                myTextCountT = attrs.getValue("t");
            }
        }

        public void endElement(String namespaceURI, String localName, String qualifiedName) {

        }
    }



}
