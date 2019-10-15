package com.amazonaws.lambda.demo;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

public class DYNAMODBCalls extends LambdaFunctionHandler {
	static  String JWT1=null;
	static String DynamoPMS = null;
	static String DynamoGRVITY = null;
	static ArrayList<String> list = new ArrayList<String>();
	static ArrayList<String> list1 = new ArrayList<String>();
	static final Logger LOGGER = LogManager.getLogger(DYNAMODBCalls.class);
	
	
	public static String dyno_query(String a,String b)
	{
		AmazonDynamoDB client1 = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client1);
		Table table = dynamoDB.getTable("Rate_codes");
	
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("Rate_code", a,"PMS_NAME", b) ;
		System.out.println("Attempting to read the item...");
		Item outcome = table.getItem(spec);
		//System.out.println("GetItem succeeded: " + outcome);
		LOGGER.info("GetItem succeeded: " + outcome);
		return (String) outcome.get("Membership");
		
		
	
	}
	
	
	
	public static void dyno_rate(String a, String b, String c) {
		AmazonDynamoDB client1 = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client1);
		Table table = dynamoDB.getTable("Rate_codes");
		PutItemOutcome outcome = table.putItem(
				new Item().withPrimaryKey("Rate_code", a).withString("PMS_NAME", b).withString("Membership", c));
		LOGGER.info("PutItem succeeded:\n" + outcome.getPutItemResult());
	}

	
	
	public static String jwt() {
		LOGGER.info("jwt()");
		
		// String tablename1=System.getenv("dynamoDB_table_name");
		String tablename1 = "Jumeriah_test";
		// String primarykey=System.getenv("primarykey");
		String primarykey = "Jumeriahtest";

		AmazonDynamoDB client1 = AmazonDynamoDBClientBuilder.defaultClient();

		DynamoDB dynamoDB = new DynamoDB(client1);

		Table table = dynamoDB.getTable(tablename1);
		String name = primarykey;
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("name", name);

		try {
			LOGGER.info("Attempting to read the item...");
			Item outcome = table.getItem(spec);
			LOGGER.info("GetItem succeeded: " + outcome);
			try {
				JWT1 = (String) outcome.get("iauth");
				LOGGER.info("JWT :" + JWT1);

				// member_API.meltan=JWT1;
				// DEFAULT.JWTT=JWT1;
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
			}
		} catch (Exception e) {
			LOGGER.info("Unable to read item: " + name);
			LOGGER.info(e.getMessage());
		}
		return JWT1;
	}
	
	
	public static void Dynamo() {

		// NEW
		AmazonDynamoDB client1 = AmazonDynamoDBClientBuilder.defaultClient();

		DynamoDB dynamoDB = new DynamoDB(client1);
		Table table = dynamoDB.getTable("Jumeriah_Compare");

		String Month = "July";
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("Month", Month);

		try {
			LOGGER.info("Attempting to read the item...");
			Item outcome = table.getItem(spec);
			LOGGER.info("GetItem succeeded: " + outcome);

			try {

				DynamoPMS = (String) outcome.get("PMS");
				DynamoGRVITY = (String) outcome.get("GRVITY");
				System.out.println("DynamoPMS :" + DynamoPMS);
				LOGGER.info("DynamoGRVITY :" + DynamoGRVITY);
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
			}
		} catch (Exception e) {
			LOGGER.info("Unable to read item: " + Month);
			LOGGER.info(e.getMessage());
		}
		String[] arr = DynamoPMS.split(",");
		String[] arr1 = DynamoGRVITY.split(",");
		for (String a : arr) {
			LOGGER.info(a);
			if (a != null) {
				list.add(a);
			}
		}
		LOGGER.info(list);
		for (String b : arr1) {
			System.out.println(b);
			if (b != null) {
				list1.add(b);
			}

		}
		LOGGER.info(list1);
	}
	
	
	
	public static void DBtoken(String token)
	{
		AmazonDynamoDB client1 = AmazonDynamoDBClientBuilder.defaultClient();
		
		DynamoDB dynamoDB = new DynamoDB(client1);
		

        Table table = dynamoDB.getTable("Jumeriah_test");

        String name = "Jumeriahtest";
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("name", name)
                .withUpdateExpression("set iauth = :r")
                .withValueMap(new ValueMap().withString(":r", token))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        try {
        	LOGGER.info("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            LOGGER.info("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
        }
        catch (Exception e) {
        	LOGGER.info("Unable to update item: " + name );
        	LOGGER.info(e.getMessage());
        }
      
	}
	
	
	public static void create(String Member_id, String PMS_ID, String PMS_NAME) {
		try {

			AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();

			DynamoDB dynamoDB = new DynamoDB(client);
			Table table = dynamoDB.getTable("PMS_GRAVTY_ID_MAPPING");
			PutItemOutcome outcome = table.putItem(new Item().withPrimaryKey("PMS_NAME", PMS_NAME)
					.withString("PMS_ID", PMS_ID).withString("Member_id", Member_id));
			LOGGER.info("PutItem succeeded:\n" + outcome.getPutItemResult());
		} catch (Exception e) {
			LOGGER.info("Unable to add item: ");
			LOGGER.info(e.getMessage());
		}
	}

}
