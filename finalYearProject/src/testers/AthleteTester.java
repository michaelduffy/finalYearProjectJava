package testers;

import java.util.Calendar;
import java.util.Date;

import classes.Athlete;

public class AthleteTester
{

	public static void main(String[] args) 
	{
		Calendar c1 = Calendar.getInstance();
		c1.set(2017,05,23);
		Date d1 = c1.getTime();
		
		Athlete a1 = new Athlete(1,"michael","duffy","9 parkview","gortlee","letterkenny","Donegal","m",d1,"0879564473","my@mail.com","pass2");
		System.out.println(a1);
	}

}
