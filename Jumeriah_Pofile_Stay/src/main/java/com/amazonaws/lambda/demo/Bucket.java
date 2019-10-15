package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GetObjectRequest;

public class Bucket extends LambdaFunctionHandler {

	static HashMap<String, String> sponsor = new HashMap<String, String>();

	public static S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
	public static String bucketName = "jumeriah-kinesis-s3-test";

	@SuppressWarnings("rawtypes")
	public static HashMap mainas() throws IOException {
		AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		fullObject = s3.getObject(new GetObjectRequest(bucketName, "Book1.csv"));
		System.out.print(fullObject);
		System.out.print("\n");
		System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
		displayTextInputStream(fullObject.getObjectContent());
		return sponsor;
	}

	private static void displayTextInputStream(InputStream input) throws IOException {
		// Read the text input stream one line at a time and display each line.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
			//System.out.println(line);
			String[] lineval = line.split(",");
			String s1 = lineval[0];
			String s2 = lineval[1];
			if (s1 != null && s2 != null) {
				sponsor.put(s1, s2);
				// System.out.println(sponsor.get(s1));
			}
		}
		//System.out.println("sponsor:" + sponsor);
	}

}
