package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class member_API extends ProfileStreamParsing {
	private static final Logger LOGGER = LogManager.getLogger(member_API.class.getName());
	@SuppressWarnings("rawtypes")
	static HashMap grvity = new HashMap();
	static String un = null;
	static String pd = null;
	static String api = null;
	static String xapikey = null;
	static String memberapi = null;
	static String api_mid = null;

	static String meltan = null;

	@SuppressWarnings({ "unused", "unchecked" })
	public static void member() {

		// JWT=LambdaFunctionHandler.JWT;


		un = System.getenv("username");
		pd = System.getenv("password");
		api = System.getenv("login_api");
		xapikey = System.getenv("x_api_key");
		memberapi = System.getenv("Api");
		memberapi=memberapi+config.tail("member_api");
		api_mid = memberapi + "/" + mid;
		LOGGER.info(api_mid);
		URL urlForGetRequest = null;
		try {
			urlForGetRequest = new URL(api_mid);
		} catch (Exception e) {

			e.printStackTrace();
			//System.out.println(e.toString());
		}

		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) urlForGetRequest.openConnection();
		} catch (IOException e) {

			e.printStackTrace();
		}
		connection.setRequestProperty("Content-Type", "application/json");
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {

			e.printStackTrace();

		}

		connection.setDoOutput(true);
		connection.setRequestProperty("x-api-key", xapikey);
		connection.setRequestProperty("Authorization", meltan);

		int responseCode = 0;
		try {
			responseCode = connection.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info(e.toString());

		}
		LOGGER.info("\nStatus Code :" + responseCode);
		try {
			LOGGER.info("Status meg :" + connection.getResponseMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (responseCode == 201 || responseCode == 200) {

			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			try {
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			LOGGER.info(response);
			String json = response.toString();
			Object obj1 = JSONValue.parse(json);
			JSONObject jsonObject1 = (JSONObject) obj1;
			JSONObject data = (JSONObject) jsonObject1.get("data");
			JSONObject user = (JSONObject) data.get("user");
			double Id = (Long) data.get("id");
			String mobile = (String) data.get("mobile");
			String email = (String) user.get("email");
			String first_name = (String) user.get("first_name");
			String last_name = (String) user.get("last_name");

			//// not compared ////
			String nationality = (String) data.get("nationality");
			String address_line1 = (String) user.get("address_line1");

			// address line not there in given xml
			// String address_line2 = (String) user.get("address_line2");
			String city = (String) user.get("city");
			String country_name = (String) user.get("country_name");
			String date_of_birth = (String) user.get("date_of_birth");
			String salutation = (String) user.get("salutation");
			String zipcode = (String) user.get("zipcode");

			// HashMap grvity = new HashMap();
			grvity.put("first_name", first_name);
			grvity.put("last_name", last_name);
			grvity.put("email", email);
			grvity.put("phoneNumber", mobile);

			/// not compared ////
			grvity.put("city", city);
			grvity.put("address_line1", address_line1);
			grvity.put("zipcode", zipcode);
			grvity.put("nationality", nationality);
			grvity.put("salutation", salutation);

			LOGGER.info("GRVITY map:" + grvity);

			String g11 = null;
			String g10 = null;
			int listl1 = list1.size();
			listl1 = listl1 - 1;
			for (int z = 0; z < listl1; z++) {
				g10 = (String) list1.get(z);
				System.out.println(g10);
				g11 = (String) grvity.get(g10);
			}

		}

		else if (responseCode == 401) {
			try {
				LOGGER.info("Token Expired ,Trying to create New Token");
				APICalls.create("MEMBER");

			} catch (Exception e) {
				System.out.print(e.toString());
				e.printStackTrace();
			}

		}

	}

}
