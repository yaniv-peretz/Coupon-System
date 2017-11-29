package webComponents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	
	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		java.util.Date endDate = new Date();
		try {
			endDate = sdf.parse("9/24/2034");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("here");
		System.out.println(endDate);
	}
}
