package com.amazonaws.lambda.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class compare_PMSandGravity extends member_API {
	private static final Logger LOGGER = LogManager.getLogger(compare_PMSandGravity.class.getName());
	public static void compare(String as) {
		
		try {
			LOGGER.info("compare_PMSandGRAVITY.JAVA");
			LOGGER.info("list:" + list);
			LOGGER.info("list1:" + list1);
			boolean match = true;
			for (int i = 0; i < list1.size() - 1; i++) {
				//System.out.println(list1.get(0));
				//System.out.println(grvity.get(list1.get(0)));
				String pmsvalue = (String) pms.get(list.get(i));
				String Gravtyvalue = (String) grvity.get(list1.get(i));
				//System.out.println("pms:" + pms);
				//System.out.println("grvity:" + grvity);
				//System.out.println("pmsvalue:" + pmsvalue);
				//System.out.println("Gravtyvalue:" + Gravtyvalue);
				if (Gravtyvalue.equals(pmsvalue)) {
					LOGGER.info(
							"Equal are :" + (String) grvity.get(list1.get(i)) + " == " + (String) pms.get(list.get(i)));
				} else {
					LOGGER.info("not Equal are :" + (String) grvity.get(list1.get(i)) + " != "
							+ (String) pms.get(list.get(i)));
					match = false;
				}
			}
			if (match != true) {
				LOGGER.info("Not matched");
				// update_API.YES();
				APICalls.MAYBE();
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
