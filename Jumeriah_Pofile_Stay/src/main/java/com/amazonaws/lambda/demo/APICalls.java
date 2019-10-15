package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class APICalls extends ProfileStreamParsing {
	static final Logger LOGGER = LogManager.getLogger(APICalls.class.getName());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void create(String method) {
		
		String un = config.tail("username");
		String pd = config.tail("password");
		String xapikey = System.getenv("x_api_key");
		String login_api = System.getenv("Api");
		login_api=login_api+config.tail("login_api");
		
		Map obj2 = new HashMap();
		obj2.put("username", un);
		obj2.put("password", pd);
		String iunpd = JSONValue.toJSONString(obj2);

		final String POST_PARAMS = iunpd;
		// grvity api And authorization to Get Token
		URL urlForGetRequest = null;
		try {
			urlForGetRequest = new URL(login_api);
		} catch (MalformedURLException e1) {
			
			e1.printStackTrace();
		}
		HttpURLConnection conection = null;
		try {
			conection = (HttpURLConnection) urlForGetRequest.openConnection();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		conection.setRequestProperty("Content-Type", "application/json");
		try {
			conection.setRequestMethod("POST");
		} catch (ProtocolException e1) {
		
			e1.printStackTrace();
		}
		conection.setDoOutput(true);
		conection.setRequestProperty("x-api-key", xapikey);

		OutputStream os = null;
		try {
			os = conection.getOutputStream();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		try {
			os.write(POST_PARAMS.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			os.flush();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		try {
			os.close();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		int responseCode = 0;
		try {
			responseCode = conection.getResponseCode();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		LOGGER.info("Status Code :" + responseCode);
		try {
			LOGGER.info("Status meg :" + conection.getResponseMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String concated = null;
		if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == 200 || responseCode == 201) { // success
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
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
			// prints Respone
			LOGGER.info("\n Response :" + response.toString());
			String json = response.toString();
			Object obj = JSONValue.parse(json);
			JSONObject jsonObject = (JSONObject) obj;
			String n = (String) jsonObject.get("token");
			// For Authentication in CallingMemberApi Class
			concated = "JWT " + n;
			DYNAMODBCalls.JWT1=concated;
			LambdaFunctionHandler.JWT=concated;
			member_API.meltan=concated;
	 		DYNAMODBCalls.DBtoken(concated);

			

	      
		} else {
			BufferedReader in1 = new BufferedReader(new InputStreamReader(conection.getErrorStream()));
			String inputLine1;
			StringBuffer response1 = new StringBuffer();
			try {
				while ((inputLine1 = in1.readLine()) != null) {
					response1.append(inputLine1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		
		try {
			DYNAMODBCalls.JWT1=concated;
			if(method.equals("MEMBER")) {
			member_API.member();
			}
			if(method.equals("BITSync")) {
				BITSync(PARSE_BIT.jsonText);
				}
			else if(method.equals("RESERVATION"))
			{
				RESERVATION_PARSE.main1(method);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	

	
	
	public static void RESApi(String qwerty, String payload) {

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, qwerty);
		String res_api = System.getenv("Api");
		res_api = res_api + config.tail("resevation_api");
		String xapikey = System.getenv("xapikey");
		Request request = new Request.Builder().url(res_api).post(body).addHeader("x-api-key", xapikey)
				.addHeader("authorization", DYNAMODBCalls.jwt()).addHeader("content-type", "application/json").build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("response message:" + response.message());
		LOGGER.info("response code:" + response.code());
		
		if (response.code() == 401) {
			try {
				LOGGER.info("Token Expired ,Trying to create New Token");
				APICalls.create(payload);
			} catch (Exception e) {
				LOGGER.info(e.toString());
				e.printStackTrace();
			}

		}
	}

	public static void BITSync(String qwerty) {
		LOGGER.info("BITSync()" );
		String bitSyncApi = System.getenv("Api");
		bitSyncApi=bitSyncApi+config.tail("bitsync_api");
		String xapikey = System.getenv("xapikey");
		
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,qwerty);
		Request request = new Request.Builder().url(bitSyncApi).post(body).addHeader("content-type", "application/json")
				.addHeader("x_api_key", xapikey).addHeader("authorization", DYNAMODBCalls.jwt()).build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		if(response.code()==400||response.code()==401)
		{
			try
			{
				create("BITSync");
			}
			catch(Exception e)
			{
				LOGGER.info(e);
			}
		}
	}

	public static void MAYBE() throws IOException {
		OkHttpClient client = new OkHttpClient();
		String updateapi = System.getenv("Api");
		updateapi = updateapi + config.tail("update_api");
		String xapikey = System.getenv("xapikey");
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, update);
		Request request = new Request.Builder().url(updateapi).post(body).addHeader("x-api-key", xapikey)
				.addHeader("authorization", DYNAMODBCalls.jwt()).addHeader("content-type", "application/json").build();
		Response response = client.newCall(request).execute();
		//System.out.println(response);
		LOGGER.info(response);
		
	}
	
	

}
