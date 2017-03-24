package testers;

import java.sql.Connection;
import java.sql.PreparedStatement;

import databaseClasses.ConnectionClass;

public class FileLoadTester 
{

	public static void main(String[] args) 
	{
		try
    	{
			    ConnectionClass cc1 = new ConnectionClass("localhost","project_database","root",""); 
			    Connection con = cc1.openConnection();
    		   /* PreparedStatement deleteTableContent = null;	
    		    String deleteTableContentString = "delete from employee";
    		    deleteTableContent = con.prepareStatement(deleteTableContentString);
    		    deleteTableContent.execute();*/
    		    
    		    PreparedStatement loadCSV = null;	
    		   // sed -i '/^\s*$/d' filename.csv
    		   /* String loadCSVstring = "LOAD DATA LOCAL INFILE '" + "F:/year 4/sem8/developmentProject/test/Book1.csv"
						+ "' INTO TABLE athlete_race_result FIELDS TERMINATED BY ','"
						+ " LINES TERMINATED BY '\n' "
						+ "IGNORE 1 LINES"
						+ "(athlete_race_no, race_id, position, ath_name,ath_start_time,overall_time,series_ath_id) ";*/
    		    String loadCSVstring = "LOAD DATA LOCAL INFILE '" + "F:/year 4/sem8/developmentProject/test/DARsplitsTest.csv"
						+ "' INTO TABLE race_split_result FIELDS TERMINATED BY ','"
						+ " LINES TERMINATED BY '\n' "
						+ "IGNORE 1 LINES"
						+ "(race_id, athlete_race_no, split_order, split_name, split_time, split_type_id) ";
    		    loadCSV= con.prepareStatement(loadCSVstring);
    		    loadCSV.execute();  		       		   
    	}
    	catch(Exception f)
    	{
    		System.out.println(f.getMessage());
    	}

	}

}
