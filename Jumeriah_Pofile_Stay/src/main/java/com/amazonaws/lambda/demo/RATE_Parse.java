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
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

public class RATE_Parse {
	static final Logger LOGGER = LogManager.getLogger(RATE_Parse.class);
	
	
	public static void mainasd(String Payload) {

		DocumentBuilder db = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(Payload));
		Document doc = null;
		try {
			doc = db.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		NodeList nodes = null;
		try {
			nodes = doc.getElementsByTagName("RateHeader");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < nodes.getLength(); i++) {

			Element element = (Element) nodes.item(i);
			NodeList hotelCode1 = element.getElementsByTagName("hotelCode");
			Element hotelCode2 = (Element) hotelCode1.item(0);
			String hotelCode = getCharacterDataFromElement(hotelCode2);
			//System.out.println("hotelCode:" + hotelCode);

			NodeList rateCode1 = element.getElementsByTagName("rateCode");
			Element rateCode2 = (Element) rateCode1.item(0);
			String rateCode = getCharacterDataFromElement(rateCode2);
			//System.out.println("rateCode: " + getCharacterDataFromElement(rateCode2));

			NodeList membership1 = element.getElementsByTagName("membership");
			Element membership2 = (Element) membership1.item(0);
			String membership = getCharacterDataFromElement(membership2);
			int mem = Integer.parseInt(membership);
			
			//System.out.println("membership: " + getCharacterDataFromElement(membership2));

			if (mem == 0) {
				membership = "N";
			} else {
				membership = "Y";
			}
			//System.out.println(hotelCode + rateCode + membership);
			LOGGER.info(rateCode, hotelCode, membership);
			DYNAMODBCalls.dyno_rate(rateCode, hotelCode, membership);

		}

	}

	public static void dyno_rate(String a, String b, String c) {
		AmazonDynamoDB client1 = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client1);
		Table table = dynamoDB.getTable("Rate_codes");
		PutItemOutcome outcome = table.putItem(
				new Item().withPrimaryKey("Rate_code", a).withString("PMS_NAME", b).withString("Membership", c));
		System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
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
