package com.amazonaws.lambda.demo;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class config extends LambdaFunctionHandler {
	
	public static void main(String[] args) {

        try (
        		OutputStream output = new FileOutputStream("config.properties")
        		) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("username", "varun.ajwani@lji.io");
            prop.setProperty("password", "Gravty1234");
            prop.setProperty("login_api", "/api/v1/login/");
            prop.setProperty("resevation_api", "/bolapi/v1/entity-data/members/booking_memberbookings/");
            prop.setProperty("bitsync_api","/bolapi/v1/bit/" );
            prop.setProperty("update_api", "/bolapi/v1/bit/");
            prop.setProperty("member_api", "/bolapi/v1/members/data");
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
        tail("password");

    }
	
	
	public static String tail(String tail)
	{
		String tail1=null;
		try (InputStream input = new FileInputStream("config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            tail1=prop.getProperty(tail);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		//System.out.println(tail1);
		 return tail1;
	}
}
