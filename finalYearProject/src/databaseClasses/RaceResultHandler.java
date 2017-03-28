package databaseClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RaceResultHandler 
{
	private Connection conn;
	
	private PreparedStatement getResult = null;	
	private String getResultString;
	
	private PreparedStatement getDirId = null;	
	private String getDirIdString;
				
	private PreparedStatement deleteResult = null;	
	private String deleteResultString;
	
	private PreparedStatement deleteSplits = null;	
	private String deleteSplitsString;
	
	private PreparedStatement getHighestPosition = null;	
	private String getHighestPositionString;
	
	private PreparedStatement getRaceTimeInSecs = null;	
	private String getRaceTimeInSecsString;
	
	private PreparedStatement getAthleteOverallTimes = null;	
	private String getAthleteOverallTimesString;
	
	private PreparedStatement editRacePoints = null;	
	private String editRacePointsString;
	
	private PreparedStatement getNoSplits = null;	
	private String getNoSplitsString;
	
	private PreparedStatement searchRaceId = null;	
	private String searchRaceIdString;
			
	private ResultSet rs1;
	private ConnectionClass cc1;
	
	
	public RaceResultHandler(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn,
							String username, String userPass)
	{
		cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn);
		//raceIdExists = false;
	}
	
	public Boolean searchForRaceIdInSplits(String raceIdIn) throws Exception
	{		
		Boolean b = false;
		
		conn = cc1.openConnection();	   	    
	    PreparedStatement searchRaceId = null;	
	   
	    String searchRaceIdString = "select DISTINCT(race_id) FROM race_split_result";
	    searchRaceId= conn.prepareStatement(searchRaceIdString);
	    rs1 = searchRaceId.executeQuery();  
	    
	    while(rs1.next())
		{
			if(raceIdIn.equals(rs1.getInt(1)+""))
			{
				b = true;
			}
			//System.out.println("(raceResulthandler/getResults)noSplits = "+noSplits);
		}		
		return b;
	}
	
	
	public void loadSplitsFromCSV(String filePathIn)throws Exception
	{		
		Boolean correctSplitsFile = true; //boolean flag variable
		Boolean firstNonHeaderLine = true; //boolean flag variable
		Boolean splitsExistInDatabase = false; //boolean flag variable
		String raceId = "";
		
		String csvFile = filePathIn;
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
        {
        	int lineCounter = 0;
            while ((line = br.readLine())!=null ) 
            {  
                // use comma as separator
                String[] result = line.split(csvSplitBy);
                
                if(lineCounter == 0) //checking first line is column headers in correct order
                {
                	if(result[0].equals("race_id")&& result[1].equals("athlete_race_no")&&
                		result[2].equals("split_order")&& result[3].equals("split_name")&&
                		result[4].equals("split_time")&& result[5].equals("split_type_id"))
                	{
                		System.out.println("correctMatch");
                	}
                	else
                	{
                		//System.out.println("InvalidMatch now breaking");
                		JOptionPane.showMessageDialog(null, "Invalid column headers!!.");
                		correctSplitsFile = false; //invalid headers = non-valid file
                		break; //break out of file read
                	}               	
                }
                else
                {
	                if(result.length > 0)//not a blank line
	                {
	                	if(firstNonHeaderLine == true)//if read line is the first non-header
	                	{
	                		raceId = result[0]; //get raceID
	                		System.out.println("race id = "+raceId);
	                		firstNonHeaderLine = false; //mark first non-header line as read
	                		//////////////////////////////////////////////////////////////////////
	                		//check if race-id already in database split results here!!!!!////////
	                		//////////////////////////////////////////////////////////////////////
	                		splitsExistInDatabase = searchForRaceIdInSplits(raceId);
	                		if(splitsExistInDatabase == true)
	                		{
	                			JOptionPane.showMessageDialog(null, "split results with race id "+raceId+" already exist in database split results.");
	                			correctSplitsFile = false; //raceId already existing in database = non-valid file
	                			break; //break out of file read
	                		}
	                		
	                	}
	                	else //all file lines after the first non-header line
	                	{
	                		//System.out.println("race id = "+raceId+", result[0] = "+result[0]);
	                		if(!result[0].equals(raceId))//if raceID found not to match the first raceID break from loop
	                		{
	                			//System.out.println("Non Matching raceIds, now breaking");
	                    		JOptionPane.showMessageDialog(null, "Non-matching race ID's found in file!!.");
	                    		correctSplitsFile = false; //invalid raceIDs = non-valid file
	                			break; //break out of file read
	                		}
	                	}	                		                	
	                }
	                else
	                {
	                	System.out.println("noValue");
	                }
                }
                lineCounter++;
            }//end of while loop

        } catch (IOException e) {
            e.printStackTrace();
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
		if(correctSplitsFile == true)//only if correctly formatted file perform upload
		{
		    conn = cc1.openConnection();	   	    
		    PreparedStatement loadCSV = null;	
		   
		    String loadCSVstring = "LOAD DATA LOCAL INFILE '" + filePathIn
					+ "' INTO TABLE race_split_result FIELDS TERMINATED BY ','"
					+ " LINES TERMINATED BY '\n' "
					+ "IGNORE 1 LINES"
					+ "(race_id, athlete_race_no, split_order, split_name, split_time, split_type_id) ";
		    loadCSV= conn.prepareStatement(loadCSVstring);
		    loadCSV.execute();  
		    JOptionPane.showMessageDialog(null, "Race split results successfully added to database.");
		}
	}
	
	public void loadOverallResultsFromCSV(String filePathIn)throws Exception
	{
		conn = cc1.openConnection();	  	    
	    PreparedStatement loadCSV = null;	
	  
	    String loadCSVstring = "LOAD DATA LOCAL INFILE '" + filePathIn
				+ "' INTO TABLE athlete_race_result FIELDS TERMINATED BY ','"
				+ " LINES TERMINATED BY '\n' "
				+ "IGNORE 1 LINES"
				+ "(athlete_race_no, race_id, position, ath_name,ath_start_time,overall_time,series_ath_id) ";
	    loadCSV= conn.prepareStatement(loadCSVstring);
	    loadCSV.execute();  
	}
	
	public void editAthletePoints(int raceIdIn,int athNoIn,double pointsIn) throws Exception
	{
		//boolean updateExecuted
		editRacePointsString = "UPDATE athlete_race_result SET ath_race_points = ? WHERE race_id = ? AND athlete_race_no = ? ";
		conn = cc1.openConnection();
		editRacePoints = conn.prepareStatement(editRacePointsString);
		editRacePoints.setDouble(1,pointsIn);
		editRacePoints.setInt(2,raceIdIn);
		editRacePoints.setInt(3,athNoIn);
		editRacePoints.execute();
									
		//return rs1;		
	}
	
	public ResultSet getAllAthleteOverallTime(int raceIdIn) throws Exception
	{
		getAthleteOverallTimesString = "select athlete_race_no, TIME_TO_SEC(overall_time) from athlete_race_result where race_id = ?";
		conn = cc1.openConnection();
		getAthleteOverallTimes = conn.prepareStatement(getAthleteOverallTimesString);
		getAthleteOverallTimes.setInt(1,raceIdIn);
		rs1 = getAthleteOverallTimes.executeQuery();
									
		return rs1;		
	}
	
	//method to return all SplitTypes in database
	public ResultSet getResults(int idIn) throws Exception
	{	
		int noSplits=0;
		getNoSplitsString = "SELECT DISTINCT(no_recorded_splits) from race WHERE race_id = ?";
		conn = cc1.openConnection();
		getNoSplits = conn.prepareStatement(getNoSplitsString);
		getNoSplits.setInt(1, idIn);
		rs1 = getNoSplits.executeQuery();
		
		while(rs1.next())
		{
			noSplits = rs1.getInt(1);
			//System.out.println("(raceResulthandler/getResults)noSplits = "+noSplits);
		}
		
		if(noSplits>1)
		{
		    getResultString ="SELECT athlete_race_result.position,"
		    		+ "athlete_race_result.ath_name, "
		    		+ "athlete_race_result.athlete_race_no,"
		    		+ "GROUP_CONCAT(split_time Order BY split_order)AS 'splits',"
		    		+ "athlete_race_result.overall_time, "
		    		+ "athlete_race_result.ath_race_points, "
		    		+ "athlete_race_result.series_ath_id "
		    		+ "FROM athlete_race_result, race_split_result "
		    		+ "WHERE athlete_race_result.athlete_race_no = race_split_result.athlete_race_no "
		    		+ "AND athlete_race_result.race_id = ? "
		    		+ "GROUP BY race_split_result.athlete_race_no "
		    		+ "ORDER BY position";
		}
		else
		{
			getResultString ="SELECT position,ath_name, athlete_race_no, overall_time, ath_race_points, series_ath_id"
					+ " FROM athlete_race_result "
					+ "WHERE race_id = ? "
					+ "ORDER BY position";
		}
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getResult = conn.prepareStatement(getResultString);
		getResult.setInt(1,idIn);
		rs1 = getResult.executeQuery();
									
		return rs1;					
	}
	
	public ResultSet getRaceNames() throws Exception
	{		
	    getResultString ="select race_name,race_id from race";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getResult = conn.prepareStatement(getResultString);
		rs1 = getResult.executeQuery();
									
		return rs1;					
	}
	
	public ResultSet getRaceNamesByDirEmail(String emailIn) throws Exception
	{
		getDirIdString ="select director_id from director where email = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getDirId = conn.prepareStatement(getDirIdString);
		getDirId.setString(1,emailIn);
		rs1 = getDirId.executeQuery();
		
		int userDirId = 0;
		while(rs1.next())
		{
			userDirId = rs1.getInt(1);
			System.out.println("getRaceNamesByDirEmail - dirId = "+userDirId);
		}
		
		  	getResultString ="select race_name,race.race_id "
		  			+ "from race,director_race where race.race_id = director_race.race_id AND "
		  			+ "director_race.director_id = ?";
		 
			getResult = conn.prepareStatement(getResultString);
			getResult.setInt(1,userDirId);
			rs1 = getResult.executeQuery();
	    									
		return rs1;					
	}
	
	
	
	//method to delete a SplitType in database
	public int deleteOverallRaceResult(int raceIdIn)throws Exception
	{
		int objectDeletedId = -1;
    	int deleteCheck = 0;
    	
		deleteResultString = "DELETE FROM athlete_race_result WHERE race_id = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		deleteResult =conn.prepareStatement(deleteResultString);
		deleteResult.setInt(1,raceIdIn);
    	
    	deleteCheck = deleteResult.executeUpdate(); //should return 1 for successful delete otherwise 0;
    	if(deleteCheck !=0)
    	{
        	 objectDeletedId = raceIdIn;
      	}
    	deleteResult.close();
    	final int I = objectDeletedId;
    	
    	return I;
	}
	
	public int deleteRaceSplits(int raceIdIn)throws Exception
	{
		int objectDeletedId = -1;
    	int deleteCheck = 0;
    	
		deleteSplitsString = "DELETE FROM race_split_result WHERE race_id = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		deleteSplits =conn.prepareStatement(deleteSplitsString);
		deleteSplits.setInt(1,raceIdIn);
    	
    	deleteCheck = deleteSplits.executeUpdate(); //should return 1 for successful delete otherwise 0;
    	if(deleteCheck !=0)
    	{
        	 objectDeletedId = raceIdIn;
      	}
    	deleteSplits.close();
    	final int I = objectDeletedId;
    	
    	return I;
	}
	
	public int getHighestRacePosition(int idIn) throws Exception
	{
		int highestPosition = 0;
		getHighestPositionString = "select MAX(position) from athlete_race_result where race_id = ?";
		conn = cc1.openConnection();
		getHighestPosition = conn.prepareStatement(getHighestPositionString);
		getHighestPosition.setInt(1, idIn);
		rs1 = getHighestPosition.executeQuery();
		
		while(rs1.next())
		{
			highestPosition = rs1.getInt(1);
		}
		
		return highestPosition;
	}
	
	public int getAthRaceTimeInSecs(int raceIdIn, int positionIn) throws Exception
	{
		int raceTimeInSecs = 0;
		getRaceTimeInSecsString = "SELECT TIME_TO_SEC(overall_time) FROM athlete_race_result where position = ? and race_id = ?";
		conn = cc1.openConnection();
		getRaceTimeInSecs = conn.prepareStatement(getRaceTimeInSecsString);
		getRaceTimeInSecs.setInt(1, positionIn);
		getRaceTimeInSecs.setInt(2, raceIdIn);
		rs1 = getRaceTimeInSecs.executeQuery();
		
		while(rs1.next())
		{
			raceTimeInSecs = rs1.getInt(1);
		}
		
		return raceTimeInSecs;
	}
										
}


