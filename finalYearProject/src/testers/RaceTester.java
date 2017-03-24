package testers;


import java.util.Calendar;
import java.util.Date;

import classes.Race;

public class RaceTester
{

	public static void main(String[] args) 
	{
		
		
		//System.out.println(d1);
		//Race r1 = new Race();
		Calendar c1 = Calendar.getInstance();
		c1.set(2017,05,23);
		Date d1 = c1.getTime();
		System.out.println(d1);
		Boolean b1 = true;
		
		Race r1 = new Race(11,"beast of east","wicklow",d1,b1,2);
		System.out.println(r1);

	}

}
