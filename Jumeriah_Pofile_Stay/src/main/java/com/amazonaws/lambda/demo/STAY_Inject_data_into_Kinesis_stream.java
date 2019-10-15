package com.amazonaws.lambda.demo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.*;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

public class STAY_Inject_data_into_Kinesis_stream {
	
	
	    static AWSCredentials credentials = null;
	    static AmazonKinesis amazonKinesis=null;
	    public static void main(String args[]) {
	        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAVTGYUBD2BHZE5ZSZ", "ezqv0nESFquXh7uW6OCp0He5CprXY4CktA3s57F1");
	        amazonKinesis = AmazonKinesisClientBuilder
	                .standard().withRegion(Regions.US_WEST_2)
	                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
	                .build();
	        String myData ="<?xml version = '1.0' encoding = 'UTF-8'?><?Label JET|STAY|345762807|SUCCESS?>\n" + 
	        		"\n" + 
	        		"<Stay xmlns=\"stay.fidelio.3.0\">\n" + 
	        		"\n" + 
	        		"   <HotelReference>\n" + 
	        		"\n" + 
	        		"      <hotelCode>JET</hotelCode>\n" + 
	        		"\n" + 
	        		"   </HotelReference>\n" + 
	        		"\n" + 
	        		"   <resortBookNo>69053537</resortBookNo>\n" + 
	        		"\n" + 
	        		"   <pmsGuestId>2ASA71Q</pmsGuestId>\n" + 
	        		"\n" + 
	        		"   <actualArrivalDate>2019-08-06T00:00:00.000</actualArrivalDate>\n" + 
	        		"\n" + 
	        		"   <actualDepartureDate>2019-08-10T03:00:00.000</actualDepartureDate>\n" + 
	        		"\n" + 
	        		"   <bookDate>2018-01-10T00:00:00.000</bookDate>\n" + 
	        		"\n" + 
	        		"   <bookedDepartureDate>2018-01-14T00:00:00.000</bookedDepartureDate>\n" + 
	        		"\n" + 
	        		"   <bookedArrivalDate>2018-01-14T00:00:00.000</bookedArrivalDate>\n" + 
	        		"\n" + 
	        		"   <compYN>N</compYN>\n" + 
	        		"\n" + 
	        		"   <adults>2</adults>\n" + 
	        		"\n" + 
	        		"   <children>0</children>\n" + 
	        		"\n" + 
	        		"   <crsBookNo>2255745</crsBookNo>\n" + 
	        		"\n" + 
	        		"   <pmsRoomCategory>K1DXD</pmsRoomCategory>\n" + 
	        		"\n" + 
	        		"   <roomNumber>111611</roomNumber>\n" + 
	        		"\n" + 
	        		"   <pmsRateCode>STAD0</pmsRateCode>\n" + 
	        		"\n" + 
	        		"   <pmsRate currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"      <valueNum>900</valueNum>\n" + 
	        		"\n" + 
	        		"   </pmsRate>\n" + 
	        		"\n" + 
	        		"   <pmsCurrencyCode>AED</pmsCurrencyCode>\n" + 
	        		"\n" + 
	        		"   <sourceCode>JUC</sourceCode>\n" + 
	        		"\n" + 
	        		"   <marketCode>GRPC</marketCode>\n" + 
	        		"\n" + 
	        		"   <pmsGroupId>71866036</pmsGroupId>\n" + 
	        		"\n" + 
	        		"   <pmsCompanyId>48413</pmsCompanyId>\n" + 
	        		"\n" + 
	        		"   <channel>JUM</channel>\n" + 
	        		"\n" + 
	        		"   <paymentMethod>CA</paymentMethod>\n" + 
	        		"\n" + 
	        		"   <rateCurrencyCode>AED</rateCurrencyCode>\n" + 
	        		"\n" + 
	        		"   <allotmentCode>SCB150118</allotmentCode>\n" + 
	        		"\n" + 
	        		"   <totalRevenue>3110.65</totalRevenue>\n" + 
	        		"\n" + 
	        		"   <roomRevenue>2906.55</roomRevenue>\n" + 
	        		"\n" + 
	        		"   <fbRevenue>53.05</fbRevenue>\n" + 
	        		"\n" + 
	        		"   <StayRevenues>\n" + 
	        		"\n" + 
	        		"      <StayRevenue>\n" + 
	        		"\n" + 
	        		"         <revenueType>ACC</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>2142.32</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayRevenue>\n" + 
	        		"\n" + 
	        		"      <StayRevenue>\n" + 
	        		"\n" + 
	        		"         <revenueType>AGENCY</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>57.14</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayRevenue>\n" + 
	        		"\n" + 
	        		"      <StayRevenue>\n" + 
	        		"\n" + 
	        		"         <revenueType>ETSUSHI</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>36.73</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayRevenue>\n" + 
	        		"\n" + 
	        		"      <StayRevenue>\n" + 
	        		"\n" + 
	        		"         <revenueType>MUNDO</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>-2.23</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayRevenue>\n" + 
	        		"\n" + 
	        		"      <StayRevenue>\n" + 
	        		"\n" + 
	        		"         <revenueType>MUNFEE</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>184.53</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayRevenue>\n" + 
	        		"\n" + 
	        		"      <StayRevenue>\n" + 
	        		"\n" + 
	        		"         <revenueType>PAYMENT</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>-2239.15</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayRevenue>\n" + 
	        		"\n" + 
	        		"      <StayRevenue>\n" + 
	        		"\n" + 
	        		"         <revenueType>SERVICE</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>263.61</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayRevenue>\n" + 
	        		"\n" + 
	        		"      <StayRevenue>\n" + 
	        		"\n" + 
	        		"         <revenueType>VAT</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>145.01</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayRevenue>\n" + 
	        		"\n" + 
	        		"   </StayRevenues>\n" + 
	        		"\n" + 
	        		"   <StayWindowRevenues>\n" + 
	        		"\n" + 
	        		"      <StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>ETSUSHI</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>36.73</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"      <StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>MUNFEE</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>184.53</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"      <StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>AGENCY</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>57.14</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"      <StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>PAYMENT</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>-2239.15</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"      <StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>SERVICE</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>263.61</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"      <StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>MUNDO</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>-2.23</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"      <StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>VAT</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>145.01</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"      <StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>ACC</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>2142.32</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </StayWindowRevenue>\n" + 
	        		"\n" + 
	        		"   </StayWindowRevenues>\n" + 
	        		"\n" + 
	        		"   <resortLegNo>1</resortLegNo>\n" + 
	        		"\n" + 
	        		"   <reservationID>69053537</reservationID>\n" + 
	        		"\n" + 
	        		"   <confirmationID>2255745.1</confirmationID>\n" + 
	        		"\n" + 
	        		"   <mfConfirmationLegNo>1</mfConfirmationLegNo>\n" + 
	        		"\n" + 
	        		"   <reservationStatus>CHECKED OUT</reservationStatus>\n" + 
	        		"\n" + 
	        		"   <DailyRates>\n" + 
	        		"\n" + 
	        		"      <DailyRate>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <pmsRateCode>STAD0</pmsRateCode>\n" + 
	        		"\n" + 
	        		"         <marketCode>GRPC</marketCode>\n" + 
	        		"\n" + 
	        		"         <pmsRate currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>900</valueNum>\n" + 
	        		"\n" + 
	        		"         </pmsRate>\n" + 
	        		"\n" + 
	        		"         <roomNumber>111611</roomNumber>\n" + 
	        		"\n" + 
	        		"         <resourceId>89851960</resourceId>\n" + 
	        		"\n" + 
	        		"         <pseudoRoom>0</pseudoRoom>\n" + 
	        		"\n" + 
	        		"         <pmsRoomCategory>K1DXD</pmsRoomCategory>\n" + 
	        		"\n" + 
	        		"         <rateRoomType>K1DXD</rateRoomType>\n" + 
	        		"\n" + 
	        		"      </DailyRate>\n" + 
	        		"\n" + 
	        		"   </DailyRates>\n" + 
	        		"\n" + 
	        		"   <DailyRevenues>\n" + 
	        		"\n" + 
	        		"      <DailyRevenue>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>MUNDO</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>-2.23</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </DailyRevenue>\n" + 
	        		"\n" + 
	        		"      <DailyRevenue>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>MUNFEE</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>184.53</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </DailyRevenue>\n" + 
	        		"\n" + 
	        		"      <DailyRevenue>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>SERVICE</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>263.61</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </DailyRevenue>\n" + 
	        		"\n" + 
	        		"      <DailyRevenue>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>ACC</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>2142.32</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </DailyRevenue>\n" + 
	        		"\n" + 
	        		"      <DailyRevenue>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>VAT</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>145.01</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </DailyRevenue>\n" + 
	        		"\n" + 
	        		"      <DailyRevenue>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>ETSUSHI</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>36.73</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </DailyRevenue>\n" + 
	        		"\n" + 
	        		"      <DailyRevenue>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>AGENCY</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>57.14</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </DailyRevenue>\n" + 
	        		"\n" + 
	        		"      <DailyRevenue>\n" + 
	        		"\n" + 
	        		"         <trxDate>2018-01-14</trxDate>\n" + 
	        		"\n" + 
	        		"         <folioWindow>1</folioWindow>\n" + 
	        		"\n" + 
	        		"         <revenueType>PAYMENT</revenueType>\n" + 
	        		"\n" + 
	        		"         <Amount currencyCode=\"AED\">\n" + 
	        		"\n" + 
	        		"            <valueNum>-2239.15</valueNum>\n" + 
	        		"\n" + 
	        		"         </Amount>\n" + 
	        		"\n" + 
	        		"      </DailyRevenue>\n" + 
	        		"\n" + 
	        		"   </DailyRevenues>\n" + 
	        		"\n" + 
	        		"   <hasDailyRooms>1</hasDailyRooms>\n" + 
	        		"\n" + 
	        		"   <rateRoomType>K1DXD</rateRoomType>\n" + 
	        		"\n" + 
	        		"</Stay>" ;
	       
        
	        //can be json
	        PutRecordRequest putRecordRequest = new PutRecordRequest();
	        //name of aws stream you created
	        putRecordRequest.setStreamName("JumeriahTestStream"); 
	        putRecordRequest.setPartitionKey("session" + "S1");
	        putRecordRequest.withData(ByteBuffer.wrap(myData.getBytes()));
	        PutRecordResult putRecordResult = amazonKinesis.putRecord(putRecordRequest);
	        //System.out.println(putRecordResult.getSequenceNumber());
	        
	        System.out.println("Successfully putrecord, partition 	 : " + putRecordRequest.getPartitionKey()
            + ", ShardID : " + putRecordResult.getShardId() + ", Sequence No : "+ putRecordResult.getSequenceNumber());
	        
	    
	        
	        
	        ListShardsResult result = amazonKinesis.listShards(new ListShardsRequest()
                    .withStreamName("Jumeirah_Bit_Sync")
                    .withMaxResults(10));
	        System.out.println(result);	   
	        
            
	        /*
	        for (int j = 0; j < 20; j++) {
	        	
	            PutRecordRequest putRecordRequest = new PutRecordRequest();
	            putRecordRequest.setStreamName("Jumeriah_Common");
	           
	           // putRecordRequest.setShardId("shardId-000000000000");
	            putRecordRequest.setData(ByteBuffer.wrap(myData.getBytes()));   
	            putRecordRequest.setPartitionKey(String.format("partitionKey-%d", j));  
	            
	            ListShardsResult result = amazonKinesis.listShards(new ListShardsRequest()
	                    .withStreamName("Jumeriah_Common")
	                    .withMaxResults(10));
		        System.out.println(result);	
	            
	           PutRecordResult putRecordResult = amazonKinesis.putRecord(putRecordRequest);;
	            System.out.println("Successfully putrecord, partition 	 : " + putRecordRequest.getPartitionKey()
	                    + ", ShardID : " + putRecordResult.getShardId() + ", Sequence No : "+ putRecordResult.getSequenceNumber());
	        
	           
	        } 	
	        */
	     //   getDataCount("shardId-000000000000","Jumeriah_Common");
	      
	    }
	    
	  
	    public static int getDataCount(String shardId, String streamName) {
	        int dataCount = 0;
	        String shardIterator;
	      GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest();
	      getShardIteratorRequest.setStreamName(streamName);
	      getShardIteratorRequest.setShardId(shardId);
	      getShardIteratorRequest.setShardIteratorType(ShardIteratorType.LATEST);


	      GetShardIteratorResult getShardIteratorResult = amazonKinesis.getShardIterator(getShardIteratorRequest);
	      shardIterator = getShardIteratorResult.getShardIterator();
	      GetRecordsRequest getRecordsRequest = new GetRecordsRequest();
	      getRecordsRequest.setShardIterator(shardIterator);
	      getRecordsRequest.setLimit(1000);

	      GetRecordsResult getRecordsResult = amazonKinesis.getRecords(getRecordsRequest);
	      List<Record> records = getRecordsResult.getRecords();
	      if(!records.isEmpty() && records.size() > 0){
	          dataCount = records.size();
	          Iterator<Record> iterator = records.iterator();
	          while(iterator.hasNext()) {
	              Record record = iterator.next();
	              byte[] bytes = record.getData().array();
	              String recordData = new String(bytes);
	              System.out.println("Shard Id. :"+shardId+"Seq. No. is : "+"  Record data :"+recordData);
	          }
	      }


	    return dataCount;
	}
	
	        
	    }
	    
	    
	


