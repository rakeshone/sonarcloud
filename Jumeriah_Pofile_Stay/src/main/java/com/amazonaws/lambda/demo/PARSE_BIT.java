package com.amazonaws.lambda.demo;

import java.util.Date;
import java.io.StringReader;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;





public class PARSE_BIT extends LambdaFunctionHandler
{
	private static final Logger LOGGER = LogManager.getLogger(PARSE_BIT.class.getName());
	static String jsonText=null;
	
	@SuppressWarnings("unused")
	public static void parse(String payload) throws ParserConfigurationException{
    	//public static void main(String args[]) {
    	
    	//
		LOGGER.info("Parsing Payload");
    DocumentBuilder db = null;
	try {
		db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	} catch (ParserConfigurationException e1) {
		e1.printStackTrace();
	}
    InputSource is = new InputSource();
	 is.setCharacterStream(new StringReader(payload));
	 Document doc=null;
	 try{
		 doc= db.parse(is);
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
    NodeList nodes =null;
    try {
    nodes= doc.getElementsByTagName("Stay");
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
    for (int i = 0; i < nodes.getLength(); i++) {
    	
	      Element element = (Element) nodes.item(i);
	      // Tempararly we are taking pmsguest id as memberid later replace with the real one
	      NodeList pmsGuestId1 = element.getElementsByTagName("pmsGuestId");
	      Element line1 = (Element) pmsGuestId1.item(0);
	      //System.out.println("pmsGuestId: " + getCharacterDataFromElement(line1));
	     String pmsGuestId = getCharacterDataFromElement(line1);
	      // Iam assuming bookDate == "h_bit_date"
	      NodeList bookDate1 = element.getElementsByTagName("bookDate");
	      Element line2 = (Element) bookDate1.item(0);
	      String bookDate = getCharacterDataFromElement(line2);
	      //System.out.println("bookDate: " + getCharacterDataFromElement(line2));
	      
	      //Iam assuming actualArrivalDate== "h_start_date"
	      NodeList actualArrivalDate1 = element.getElementsByTagName("actualArrivalDate");
	      Element line3 = (Element) actualArrivalDate1.item(0);
	      //System.out.println("actualArrivalDate: " + getCharacterDataFromElement(line3));
	      String actualArrivalDate = getCharacterDataFromElement(line3);
	      //Iam assuming actualDepartureDate== "h_end_date"
	      NodeList actualDepartureDate1 = element.getElementsByTagName("actualDepartureDate");
	      Element line4 = (Element) actualDepartureDate1.item(0);
	     // System.out.println("actualDepartureDate: " + getCharacterDataFromElement(line4));
	      String actualDepartureDate = getCharacterDataFromElement(line4);
	      //Formatting PMS actualDepartureDate to comply with gravty
	     StringBuilder date = new StringBuilder(actualDepartureDate);
	     date.setCharAt(16, '-');
	     date.setCharAt(19, ':');
	    // System.out.println("Date Length:"+date.length());
	     String  date1=date.toString();
	     date1=date1.substring(0, 22);
	     // System.out.println("date1:"+date1);
		   // "h_sponsor_id":1 may be missing in pms so get from environment variable
	      String h_sponsor_id= System.getenv("h_sponsor_id"); 
	    //GRAVTY "h_bit_category" may be missing in pms
	      String h_bit_category="ACCRUAL";
	     // System.out.println("h_bit_category:"+h_bit_category);
	     //"h_bit_type": "STAY" may be missing in pms
	      String h_bit_type="STAY";
	      //System.out.println("h_bit_type:"+h_bit_type);
	      // "h_location": "HQ" may be missing in pms
	      //String h_location= "HQ";
	      String h_location=System.getenv("h_location"); 
	     // System.out.println("h_location:"+h_location);
	      //Iam assuming totalRevenue== "h_bit_amount"
	      NodeList totalRevenue1 = element.getElementsByTagName("totalRevenue");
	      Element line5 = (Element) totalRevenue1.item(0);
	      //System.out.println("totalRevenue: " + getCharacterDataFromElement(line5));
	      String totalRevenue = getCharacterDataFromElement(line5);
	      // "h_program_id":17 may be missing in pms so get from environment variable
	      String h_program_id= System.getenv("h_program_id"); 
	      
	    //"market_group"=="marketCode"
	      NodeList market_group1 = element.getElementsByTagName("marketCode");
	      Element line7 = (Element) market_group1.item(0);
	      //System.out.println("marketCode ==market_group :" + getCharacterDataFromElement(line7));
	      String market_group = getCharacterDataFromElement(line7);
	     
	     
	      // "rate_group"=="pmsRoomCategory"
	      NodeList pmsRoomCategory1 = element.getElementsByTagName("pmsRoomCategory");
	      Element line8 = (Element) pmsRoomCategory1.item(0);
	      //System.out.println("rate_group==pmsRoomCategory :" + getCharacterDataFromElement(line8));
	      String rate_group = getCharacterDataFromElement(line8);
	      
	     
	    
	      //"rate_code"=="pmsRateCode"
	      NodeList pmsRateCode1 = element.getElementsByTagName("pmsRateCode");
	      Element line9 = (Element) pmsRateCode1.item(0);
	      //System.out.println("rate_code==pmsRateCode :" + getCharacterDataFromElement(line9));
	      String rate_code = getCharacterDataFromElement(line9);
	     
			NodeList hotelCode1 = element.getElementsByTagName("hotelCode");
			Element hotelCode2 = (Element) hotelCode1.item(0);
			String hotelCode = getCharacterDataFromElement(hotelCode2);
	      
	      
	     
	     
	      NodeList rateCurrencyCode1 = element.getElementsByTagName("rateCurrencyCode");
	      Element line6 = (Element) rateCurrencyCode1.item(0);
	      //System.out.println("rateCurrencyCode: " + getCharacterDataFromElement(line6));
	      String rateCurrencyCode = getCharacterDataFromElement(line6);
	      //  "point_earn_preference": "JH", may be missing
	      String  point_earn_preference=System.getenv("point_earn_preference");
	     // String h_sponsor_id=System.getenv("h_sponsor_id");
	      //  "h_bit_source": "PMS", may be missing
	      String h_bit_source="PMS";
	     //System.out.print("h_bit_source:"+h_bit_source);
	      //  "h_no_of_nights": 6, may be missing
	      SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
	      float h_no_of_nights = 0;
	 	 try {
	 	       Date dateBefore = myFormat.parse(actualArrivalDate);
	 	     
	 	       Date dateAfter = myFormat.parse(actualDepartureDate);
	 	       long difference = dateAfter.getTime() - dateBefore.getTime();
	 	       float daysBetween = (difference / (1000*60*60*24));
	 	       h_no_of_nights=daysBetween;
	 	       //System.out.println("Number of Days between dates: "+h_no_of_nights);
	 	 } catch (Exception e) {
	 	       e.printStackTrace();
	 	 }
	      // "h_rep_id": 1, may be missing (may be this gives error in int and string type)
	     // String h_rep_id="1";
	      //"h_rep_email": "varun.ajwani@lji.io", may be missing
	 	 String l_quantity="1";
	 	// System.out.println("l_quantity:"+l_quantity);
	 	 
	 	ArrayList<String> amount = new ArrayList<String>();
		ArrayList<String> category = new ArrayList<String>();
	 	 
	 	NodeList StayRevenuesList = doc.getElementsByTagName("StayRevenues");
	      if (StayRevenuesList != null && StayRevenuesList.getLength() > 0) {
				Node StayRevenuesNode = StayRevenuesList.item(0);
				NodeList StayRevenue1 = ((Element) StayRevenuesNode).getElementsByTagName("StayRevenue");

				for (int j = 0; j < StayRevenue1.getLength(); j++) {
					Node nStayRevenue = StayRevenue1.item(j);
					Element eStayRevenue = (Element) nStayRevenue;
					String a= getValue(eStayRevenue, "valueNum");
					String b = getValue(eStayRevenue, "revenueType");
					
					amount.add(a);
					category.add(b);
				}
			}
	      //LOGGER.info("category:"+category);
	      //LOGGER.info("amount:"+amount);
	      
    
   
    Map<String, Object> obj=new HashMap<String, Object>();   

	System.out.println("json.java");
	obj.put("h_member_id",pmsGuestId);
	obj.put("h_bit_date",date1);
	obj.put("h_start_date",actualArrivalDate);
	obj.put("h_end_date",actualDepartureDate);
	//takes value from environmental variables  temporarily hard-coding
	//obj.put("h_sponsor_id",h_sponsor_id);
	
	//obj.put("h_sponsor_id",1);
	
	obj.put("h_bit_category",h_bit_category);
	obj.put("h_bit_type",h_bit_type);
	obj.put("h_location",h_location);
	obj.put("h_bit_amount",totalRevenue);
	////takes value from environmental variables  temporarly harcoding
	obj.put("h_program_id",h_program_id);
	
	//obj.put("h_program_id",17);

	
	obj.put("market_group",market_group);
	obj.put("rate_group",rate_group);
	obj.put("rate_code",rate_code);
	obj.put("h_bit_currency",rateCurrencyCode);
	obj.put("h_bit_amount_currency",rateCurrencyCode);
	

	////takes value from environmental variables  temporarly harcoding
	obj.put("point_earn_preference",point_earn_preference);
	
	//obj.put("point_earn_preference","Jumeriah_Points");
	
	obj.put("h_bit_source",h_bit_source);
	obj.put("h_no_of_nights",h_no_of_nights);
	
	obj.put("rate_code_eligible", (DYNAMODBCalls.dyno_query(rate_code,hotelCode)));
	
	obj.put("h_sponsor_id",Integer.parseInt((String) sponsor_u.get(hotelCode)));
	//obj.put("h_rep_id",1);
	//obj.put("h_rep_email","varun.ajwani@lji.io");
	//System.out.println("amount.size():"+amount.size());
	//lines
	List<Object> list = new ArrayList<Object>();
		//	list.add("3");
	for(int z=0;z<amount.size();z++)
	{
		 Map<String, String> objlines=new HashMap<String, String>();   
		objlines.put("l_quantity",l_quantity);
		String la=amount.get(z);
		objlines.put("l_amount", la);
		String lpcg=category.get(z);
		objlines.put("l_product_category", lpcg);

		String linesttotext= JSONValue.toJSONString(objlines); 
		//System.out.println("linesttotext:"+linesttotext);
		list.add(objlines);
	}
	obj.put("lines",list);
	jsonText = JSONValue.toJSONString(obj);  
	LOGGER.info("Payload:"+jsonText);
	
	APICalls.BITSync(jsonText);
	/*
	 String bitSyncApi = System.getenv("bit_SyncApi");
 	String xapikey= System.getenv("xapikey"); 
		System.out.println("PARSEBIT.java");
	 OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,jsonText);
		Request request = new Request.Builder()
				  .url(bitSyncApi)
				  .post(body)
				  .addHeader("content-type", "application/json")
				  .addHeader("x_api_key", xapikey)
				  .addHeader("authorization",JWT_token.JWT1 )
				  .build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response);
	 */
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

