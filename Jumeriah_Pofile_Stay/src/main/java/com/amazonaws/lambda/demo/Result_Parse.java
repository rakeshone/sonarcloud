package com.amazonaws.lambda.demo;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Result_Parse extends DYNAMODBCalls {

	private static final Logger LOGGER = LogManager.getLogger(Result_Parse.class.getName());
	// public static void main(String[] args) {
	public static void main1(String payload) {

		DocumentBuilder db = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		InputSource is = new InputSource();
		// is.setCharacterStream(new StringReader(mydata));
		is.setCharacterStream(new StringReader(payload));
		Document doc = null;
		try {
			doc = db.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		NodeList nodes = null;
		try {
			nodes = doc.getElementsByTagName("RESULT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < nodes.getLength(); i++) {

			Element element = (Element) nodes.item(i);
			// crsID=memberid
			NodeList crsId = element.getElementsByTagName("crsId");
			Element line1 = (Element) crsId.item(0);
			//System.out.println("crsId: " + getCharacterDataFromElement(line1));
			String crsId1 = getCharacterDataFromElement(line1);
			// pmsID
			NodeList pmsId = element.getElementsByTagName("pmsId");
			Element line2 = (Element) pmsId.item(0);
			String pmsId1 = getCharacterDataFromElement(line2);
			//System.out.println("pmsId: " + getCharacterDataFromElement(line2));
			// resortId
			NodeList resortId = element.getElementsByTagName("resortId");
			Element line3 = (Element) resortId.item(0);
			String resortId1 = getCharacterDataFromElement(line3);
			//System.out.println("resortId: " + getCharacterDataFromElement(line3));

			LOGGER.info(crsId1, pmsId1, resortId1);
			// For Updation In DynaamoDb
			DYNAMODBCalls.create(crsId1, pmsId1, resortId1);

		}
	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	public static String getValue(Element e, String value) {
		String result;
		try {
			result = e.getElementsByTagName(value).item(0).getTextContent();
			return result.trim().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
		} catch (NullPointerException ex) {
			return "";
		}
	}



}
