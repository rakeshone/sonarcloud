package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent.KinesisEventRecord;

public class LambdaFunctionHandler implements RequestHandler<KinesisEvent, Integer> {

	static String JWT = null;
	@SuppressWarnings("rawtypes")
	static HashMap sponsor_u = null;
	String payload = null;
	
	public Integer handleRequest(KinesisEvent event, Context context) {
		context.getLogger().log("Input: " + event);

		try {
			// DBDynamo.Dynamo();
			DYNAMODBCalls.Dynamo();
			// JWT = JWT_token.jwt();
			JWT = DYNAMODBCalls.jwt();
			Bucket.mainas();
			sponsor_u = Bucket.mainas();

		} catch (Exception e) {
			e.printStackTrace();

		}

		for (KinesisEventRecord record : event.getRecords()) {
			payload = new String(record.getKinesis().getData().array());
			String stw = payload.toString();
			context.getLogger().log("Payload size: " + stw.length());
			String xmlInfo[] = new String[2];
			try {
				BufferedReader bufReader = new BufferedReader(new StringReader(payload));
				String line = payload;

				// System.out.println(line.toString());
				while ((line = bufReader.readLine()) != null) {
					if (line.contains("?Label")) {
						String labelAttributes = line.substring(line.indexOf("Label") + 6, line.length() - 2);
						System.out.println("label : " + labelAttributes);

						xmlInfo = splitFuction(labelAttributes);
						break;
					}
				}
				if (xmlInfo[1].equals("PROFILE")) {
					ProfileStreamParsing.call(payload);
					member_API.member();
					compare_PMSandGravity.compare("noo");
				}
				if (xmlInfo[1].equals("STAY")) {
					PARSE_BIT.parse(payload);
				}
				if (xmlInfo[1].equals("RESULT")) {
					Result_Parse.main1(payload);
				}
				if (xmlInfo[1].equals("RESERVATION")) {
					RESERVATION_PARSE.main1(payload);
				}
				if (xmlInfo[1].equals("RATE")) {
					RATE_Parse.mainasd(payload);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return event.getRecords().size();
	}

	public static String[] splitFuction(String str) {
		char a[] = str.toCharArray();
		String b[] = new String[3];
		String temp = "";
		int j = 0;
		for (int i = 0; i < str.length(); i++) {
			if (a[i] == '|' || i == str.length() - 1) {
				b[j] = temp;
				j++;
				temp = "";
			} else {
				temp = temp + a[i];
			}
			if (j == 3)
				break;
		}
		return b;
	}

}