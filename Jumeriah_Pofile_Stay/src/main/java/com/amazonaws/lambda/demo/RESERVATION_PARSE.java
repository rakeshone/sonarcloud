package com.amazonaws.lambda.demo;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONValue;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class RESERVATION_PARSE extends LambdaFunctionHandler {
	// public static void main(String args[])
	static final Logger LOGGER = LogManager.getLogger(RESERVATION_PARSE.class.getName());

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })

	public static void main1(String payload) {

		String memberId = null;
		String originalBookingDate = null;
		String arrivalTime = null;
		String departureTime = null;
		String status = null;
		String roomInventoryCode = null;
		String roomID = null;
		String reservationID = null;
		String hotelCode = null;
		String nameOnCard=null;

		LOGGER.info("");
		DocumentBuilder db = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(payload));
		Document doc = null;
		try {
			doc = db.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		NodeList nodes = null;
		try {
			nodes = doc.getElementsByTagName("Reservation");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < nodes.getLength(); i++) {

			Element element = (Element) nodes.item(i);

			NodeList memberList = doc.getElementsByTagName("Memberships");
			if (memberList != null && memberList.getLength() > 0) {
				Node memberNode = memberList.item(0);
				NodeList member1 = ((Element) memberNode).getElementsByTagName("Membership");
				for (int j = 0; j < member1.getLength(); j++) {
					Node nmember = member1.item(j);
					Element emember = (Element) nmember;

					memberId = getValue(emember, "accountID");

				}
			}

			// Tempararly we are taking originalBookingDate as booking_date later replace
			// with the real one
			NodeList bookDate1 = element.getElementsByTagName("originalBookingDate");
			Element line2 = (Element) bookDate1.item(0);
			originalBookingDate = getCharacterDataFromElement(line2);

			StringBuilder date = new StringBuilder(originalBookingDate);
			date.setCharAt(16, '+');
			// date.setCharAt(19, ':');
			originalBookingDate = date.toString();
			originalBookingDate = originalBookingDate.substring(0, 10);

			// Tempararly we are taking arrivalTime as checkin later replace with the real
			// one
			NodeList arrivalTime1 = element.getElementsByTagName("arrivalTime");
			Element line1 = (Element) arrivalTime1.item(0);
			arrivalTime = getCharacterDataFromElement(line1);

			// Tempararly we are taking departureTime as check_out later replace with the
			// real one
			NodeList departureTime1 = element.getElementsByTagName("departureTime");
			Element line3 = (Element) departureTime1.item(0);
			departureTime = getCharacterDataFromElement(line3);

			NodeList reservationID1 = element.getElementsByTagName("reservationID");
			Element line8 = (Element) reservationID1.item(0);
			reservationID = getCharacterDataFromElement(line8);

			/// Status
			NodeList RoomStay1 = element.getElementsByTagName("RoomStay");
			for (int k = 0; k < RoomStay1.getLength(); k++) {
				status = RoomStay1.item(k).getAttributes().getNamedItem("mfReservationAction").getNodeValue();
			}
			// RoomInevntory Code

			NodeList roomInventoryCode1 = element.getElementsByTagName("roomInventoryCode");
			Element line6 = (Element) roomInventoryCode1.item(0);
			roomInventoryCode = getCharacterDataFromElement(line6);

			// room_number
			NodeList roomID1 = element.getElementsByTagName("roomID");
			Element line7 = (Element) roomID1.item(0);
			roomID = getCharacterDataFromElement(line7);

			NodeList hotelCode1 = element.getElementsByTagName("hotelCode");
			Element line9 = (Element) roomID1.item(0);
			hotelCode = getCharacterDataFromElement(line9);
			
			NodeList nameOnCard1 = element.getElementsByTagName("nameOnCard");
			Element nameOnCardline= (Element) nameOnCard1.item(0);
			nameOnCard = getCharacterDataFromElement(nameOnCardline);
			

		}

		// Source Code Default PMS
		String sourcecode = "MAS";
		// System.out.println("sourcecode:"+sourcecode);

		// rate plan
		String ratePlanCode = null;
		String mfMarketCode = "";
		NodeList RatePlansList = doc.getElementsByTagName("RatePlans");
		if (RatePlansList != null && RatePlansList.getLength() > 0) {
			Node RatePlansNode = RatePlansList.item(0);
			NodeList RatePlans1 = ((Element) RatePlansNode).getElementsByTagName("RatePlan");
			for (int j = 0; j < 1; j++) {
				Node nmember = RatePlans1.item(j);
				Element erateplans = (Element) nmember;

				ratePlanCode = getValue(erateplans, "ratePlanCode");
				/*
				 * mfMarketCode = getValue(erateplans, "mfMarketCode");
				 * System.out.println("mfMarketCode :" + mfMarketCode);
				 */
				LOGGER.info("marketcode blankfor now");
			}
		}

		
		
		
		
		
		
		
		// Sponsor Id default
		// String sponsor_id="4";
		// System.out.println("Sponsor_id: "+sponsor_id);

		Map obj = new HashMap();

		LOGGER.info("mapped items to json");
		obj.put("member_id", memberId);
		obj.put("booking_date", originalBookingDate);
		obj.put("check_out", arrivalTime);
		obj.put("check_in", departureTime);
		obj.put("status", status);
		obj.put("source_code", sourcecode);
		obj.put("rate_plan", ratePlanCode);
		obj.put("room_inventory_code", roomInventoryCode);
		obj.put("market_code", mfMarketCode);
		obj.put("room_number", roomID);
		obj.put("confirmation_number", reservationID);
		obj.put("booking_id", reservationID+roomID);
		obj.put("allocated_member_name",nameOnCard);
		obj.put("allocated_member_id",memberId);
		
		

		String sponsor_id = (String) sponsor_u.get(sourcecode);

		obj.put("sponsor_id", sponsor_id);
		// map to json
		String jsonText = JSONValue.toJSONString(obj);
		LOGGER.info(jsonText);
		String bitSyncApi = System.getenv("bit_SyncApi");
		String xapikey1 = System.getenv("x_api_key");

		APICalls.RESApi(jsonText, payload);

		/*
		 * OkHttpClient client = new OkHttpClient(); MediaType mediaType =
		 * MediaType.parse("application/json"); RequestBody body =
		 * RequestBody.create(mediaType, jsonText);
		 * 
		 * String res_api = System.getenv("Reservation"); res_api = res_api +
		 * "/bolapi/v1/entity-data/members/booking_memberbookings/"; String xapikey =
		 * System.getenv("xapikey"); Request request = new
		 * Request.Builder().url(res_api).post(body).addHeader("x-api-key", xapikey)
		 * .addHeader("authorization", JWT_token.jwt()).addHeader("content-type",
		 * "application/json").build();
		 * 
		 * Response response = null; try { response = client.newCall(request).execute();
		 * // Method.get("123","4"); } catch (IOException e) { e.printStackTrace(); }
		 * System.out.println("response:" + response.message());
		 * System.out.println("response:" + response.code());
		 * 
		 * if (response.code() == 401) { try {
		 * System.out.print("Token Expired ,Trying to create New Token"); //
		 * ReGenarate_JWT.create(payload,"RESERVATION"); ReGenarate_JWT.create(payload);
		 * } catch (Exception e) { System.out.print(e.toString()); e.printStackTrace();
		 * }
		 * 
		 * }
		 */

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