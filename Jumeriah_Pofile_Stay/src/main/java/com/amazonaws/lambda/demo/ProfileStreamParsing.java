package com.amazonaws.lambda.demo;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONValue;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ProfileStreamParsing extends DYNAMODBCalls {
	static final Logger LOGGER = LogManager.getLogger(ProfileStreamParsing.class.getName());
	@SuppressWarnings("rawtypes")
	static HashMap pms = new HashMap();

	static String mid = null;
	static String update = null;

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public static String call(String payload) throws Exception {
		// public static void main(String args[]) throws Exception {
		LOGGER.info("ProfileStreamparsing.java");
		LOGGER.error("ProfileStreamparsing.java");
		String xmlRecords = null;
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		// is.setCharacterStream(new StringReader(myData));
		is.setCharacterStream(new StringReader(payload));

		Document doc = db.parse(is);
		NodeList nodes = doc.getElementsByTagName("Profile");

		String ph = null;
		String pf = null;
		String fn = null;
		String ln = null;
		String nation = null;
		String em = null;
		String city = null;
		String address1 = null;
		String postalCode = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);

			// profileID
			NodeList profileID = element.getElementsByTagName("profileID");
			Element line = (Element) profileID.item(0);
			//System.out.println("profileID: " + getCharacterDataFromElement(line));
			// mid= getCharacterDataFromElement(line);
			// namePrefix
			NodeList namePrefix = element.getElementsByTagName("namePrefix");
			Element line1 = (Element) namePrefix.item(0);
			//System.out.println("namePrefix: " + getCharacterDataFromElement(line1));
			pf = getCharacterDataFromElement(line1);
			// nameFirst
			NodeList nameFirst = element.getElementsByTagName("nameFirst");
			Element line2 = (Element) nameFirst.item(0);
			System.out.println("nameFirst: " + getCharacterDataFromElement(line2));
			fn = getCharacterDataFromElement(line2);
			// nameMiddle
			NodeList nameMiddle = element.getElementsByTagName("nameMiddle");
			Element line3 = (Element) nameMiddle.item(0);
			//System.out.println("nameMiddle: " + getCharacterDataFromElement(line3));

			// nameSur
			NodeList nameSur = element.getElementsByTagName("nameSur");
			Element line4 = (Element) nameSur.item(0);
			//System.out.println("nameSur: " + getCharacterDataFromElement(line4));
			ln = getCharacterDataFromElement(line4);
			// nameOrdered
			NodeList nameOrdered = element.getElementsByTagName("nameOrdered");
			Element line5 = (Element) nameOrdered.item(0);
			//System.out.println("nameOrdered: " + getCharacterDataFromElement(line5));

			// nameOnCard
			NodeList nameOnCard = element.getElementsByTagName("nameOnCard");
			Element line6 = (Element) nameOnCard.item(0);
			//System.out.println("nameOnCard: " + getCharacterDataFromElement(line6));

			NodeList nationality = element.getElementsByTagName("nationality");
			Element line7 = (Element) nationality.item(0);
			//System.out.println("nationality: " + getCharacterDataFromElement(line7));
			nation = getCharacterDataFromElement(line7);
			NodeList emailList = doc.getElementsByTagName("ElectronicAddresses");
			if (emailList != null && emailList.getLength() > 0) {
				Node emailNode = emailList.item(0);
				NodeList email = ((Element) emailNode).getElementsByTagName("ElectronicAddress");
				for (int j = 0; j < email.getLength(); j++) {
					Node nemail = email.item(j);
					Element eEmail = (Element) nemail;
					if (getValue(eEmail, "mfPrimaryYN").equalsIgnoreCase("Y")
							|| getValue(eEmail, "mfPrimaryYN").equals("")) {
						em = getValue(eEmail, "eAddress");
						//System.out.print("Email :" + em);
					}
				}
			}
			NodeList phoneList = doc.getElementsByTagName("PhoneNumbers");
			if (phoneList != null && phoneList.getLength() > 0) {
				Node phoneNode = phoneList.item(0);
				NodeList phone = ((Element) phoneNode).getElementsByTagName("PhoneNumber");
				for (int j = 0; j < phone.getLength(); j++) {
					Node nphone = phone.item(j);
					Element ephone = (Element) nphone;
					if (getValue(ephone, "mfPrimaryYN").equalsIgnoreCase("Y")
							|| getValue(ephone, "mfPrimaryYN").equals("")) {
						ph = getValue(ephone, "phoneNumber");
						//System.out.println("phoneNumber :" + ph);
					}
				}
			}
			NodeList memberList = doc.getElementsByTagName("Memberships");
			if (memberList != null && memberList.getLength() > 0) {
				Node memberNode = memberList.item(0);
				NodeList member = ((Element) memberNode).getElementsByTagName("Membership");
				for (int j = 0; j < member.getLength(); j++) {
					Node nmember = member.item(j);
					Element emember = (Element) nmember;
					if (getValue(emember, "programCode").equalsIgnoreCase("SP")
							|| getValue(emember, "programCode").equals("")) {
						mid = getValue(emember, "accountID");
						//System.out.println("Memberid :" + mid);
					}
				}
			}

			NodeList addressList = doc.getElementsByTagName("PostalAddresses");
			if (addressList != null && addressList.getLength() > 0) {
				Node addressNode = addressList.item(0);
				NodeList address = ((Element) addressNode).getElementsByTagName("PostalAddress");
				for (int j = 0; j < address.getLength(); j++) {
					Node naddress = address.item(j);
					Element eaddress = (Element) naddress;
					if (getValue(eaddress, "mfPrimaryYN").equalsIgnoreCase("Y")
							|| getValue(eaddress, "mfPrimaryYN").equals("")) {
						city = getValue(eaddress, "city");
						address1 = getValue(eaddress, "address1");
						String stateCode = getValue(eaddress, "stateCode");
						postalCode = getValue(eaddress, "postalCode");
						String countryCode = getValue(eaddress, "countryCode");

					}
				}
			}

		}

		pms.put("nameFirst", fn);
		pms.put("nameSur", ln);
		pms.put("eAddress", em);
		pms.put("phoneNumber", ph);

		pms.put("city", city);
		pms.put("address1", address1);
		// state code not a field in gravity
		// pms.put("stateCode", stateCode);
		pms.put("postalCode", postalCode);
		// im not sure about this
		// pms.put("countryCode", countryCode);
		pms.put("nationality", nation);
		pms.put("namePrefix", pf);

		LOGGER.info("Initial Mappings are: \n" + pms);
		String p11 = null;
		String p10 = null;
		int listl = list.size();
		listl = listl - 1;
		for (int k = 0; k < listl; k++) {
			p10 = (String) list.get(k);
			LOGGER.info(p10);
			p11 = (String) pms.get(p10);
		}

		// To Get Time Stamp
		Date datee = new Date();
		long time = datee.getTime();
		//System.out.println("Time in Milliseconds: " + time);
		Timestamp ts = new Timestamp(time);
		//System.out.println("Current Time Stamp: " + ts);
		String tss = ts.toString();
		StringBuilder t_s = new StringBuilder(tss);
		t_s.setCharAt(10, 'T');
		t_s.setCharAt(16, '+');
		t_s.setCharAt(19, ':');
		t_s.setCharAt(18, '4');
		t_s.setCharAt(17, '0');
		t_s.setCharAt(20, '0');
		t_s.setCharAt(21, '0');
		//System.out.println(" t_s:" + t_s.length());
		String datets = t_s.toString();
		datets = datets.substring(0, 22);
		//System.out.println("datets:" + datets);
		//bit_date=datets;
		
		// HASH MAP
		Map objnew = new HashMap();
		objnew.put("h_bit_date", datets);
		objnew.put("h_bit_category", "SERVICE");
		objnew.put("h_start_date", null);
		objnew.put("h_end_date", null);
		objnew.put("pos_start_date", null);
		objnew.put("pos_end_date", null);
		objnew.put("h_bit_type", "PMS_PROFILE_UPDATED");
		objnew.put("pms_profile_updated", "Y");
		objnew.put("h_sponsor_id_text", null);
		objnew.put("pms_profile_updated", "Y");
		/// takes value from environmental variables
		String ss = System.getenv("h_program_id");
		objnew.put("h_program_id", ss);
		// takes value from environmental variables
		String gg = System.getenv("h_sponsor_id");
		objnew.put("h_sponsor_id", gg);
		objnew.put("h_member_id", mid);

		String jsonTextnewww = JSONValue.toJSONString(objnew);
		LOGGER.info("obj:" + jsonTextnewww);
		update = jsonTextnewww;
		return mid;
		// member_API.member(mid);
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
