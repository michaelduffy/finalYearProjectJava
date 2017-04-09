package databaseClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JOptionPane;

import classes.Race;


public class RaceHandler 
{
	private Race race1;
	private Connection conn;
	
	private PreparedStatement getRaces = null;	
	private String getRacesString;
	
	private PreparedStatement getDirId = null;	
	private String getDirIdString;
		
	private PreparedStatement getUserRaces = null;	
	private String getUserRacesString;
	
	private PreparedStatement deleteRace = null;	
	private String deleteRaceString;
	
	private PreparedStatement editRace = null;	
	private String editRaceString;
	
	private PreparedStatement addRace = null;	
	private String addRaceString;
	
	private PreparedStatement getRaceIds = null;	
	private String getRaceIdsString;
	
	private boolean raceIdExists;
	
	private ResultSet rs1;
	private ConnectionClass cc1;
	
	public RaceHandler(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn,
							String username, String userPass)
	{
		cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn);
		raceIdExists = false;
	}
	
	public ResultSet getRaceIds() throws Exception
	{
		
	    getRaceIdsString ="select DISTINCT(race_id) from race";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getRaceIds = conn.prepareStatement(getRaceIdsString);
		rs1 = getRaceIds.executeQuery();
									
		return rs1;					
	}
	
	//method to return all SplitTypes in database
	public ResultSet getRaces() throws Exception
	{
		
	    getRacesString ="select * from race";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getRaces = conn.prepareStatement(getRacesString);
		rs1 = getRaces.executeQuery();
									
		return rs1;					
	}
	
	public ResultSet getRacesByDirEmail(String email) throws Exception
	{
		
	    getDirIdString ="select director_id from director where email = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getDirId = conn.prepareStatement(getDirIdString);
		getDirId.setString(1,email);
		rs1 = getDirId.executeQuery();
		
		int userDirId = 0;
		while(rs1.next())
		{
			userDirId = rs1.getInt(1);
		}
		
		 //getUserRaceIdsString ="select race_id from director_race where race_id = ?";
		 getUserRacesString ="select * from race,director_race WHERE race.race_id = director_race.race_id AND director_race.director_id = ?";
		 getUserRaces = conn.prepareStatement(getUserRacesString);
		 getUserRaces.setInt(1,userDirId);
		 rs1 = getUserRaces.executeQuery();
										
		return rs1;					
	}
	
	//method to delete a SplitType in database
	public int deleteRace(int raceIdIn)throws Exception
	{
		int objectDeletedId = -1;
    	int deleteCheck = 0;
    	
		deleteRaceString = "DELETE FROM race WHERE race_id = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		deleteRace =conn.prepareStatement(deleteRaceString);
		deleteRace.setInt(1,raceIdIn);
    	
    	deleteCheck = deleteRace.executeUpdate(); //should return 1 for successful delete otherwise 0;
    	if(deleteCheck !=0)
    	{
        	 objectDeletedId = raceIdIn;
      	}
    	deleteRace.close();
    	final int I = objectDeletedId;
    	
    	return I;
	}
	
	//method to edit a SplitType in database
	public int editRace( int idIn, String nameIn,String locationIn,Date dateIn,
							Boolean multiWaveIn, int noRecSplitsIn) throws Exception
    {
    	int editCheck = 0; //only 0 if update does not perform
    	
    	race1 = new Race(idIn,nameIn,locationIn,dateIn,multiWaveIn,noRecSplitsIn); //to perform validation checks!!
    	editRaceString = "UPDATE race SET race_name = ?,"
    			           + "race_location = ?,"
    			           + "race_date = ?,"
    			           + "is_multi_wave = ?,"
    			           + "no_recorded_splits = ? WHERE race_id =?";
    			          // + "password = ? WHERE director_id =?";
    	conn = cc1.openConnection();
    	editRace = conn.prepareStatement(editRaceString);
    	
    	
    	editRace.setString(1, race1.getRaceName());
    	editRace.setString(2, race1.getRaceLocation());
    	editRace.setDate(3, (java.sql.Date) race1.getRaceDate());
    	editRace.setBoolean(4, race1.getIsMultiWave());
    	editRace.setInt(5, race1.getNoRecordedSplits());
    	editRace.setInt(6, race1.getRaceId());
    	   	
    	editCheck = editRace.executeUpdate(); //perform update and return confirmation 
    	editRace.close();
    	
    	final int I = editCheck;
    	
    	return I;
    }
	
	public  void addRace(int idIn, String nameIn,String locationIn,Date dateIn,Boolean multiWaveIn,
			int noRecSplitsIn) throws Exception 
    {   	
		race1 = new Race(idIn,nameIn,locationIn,dateIn,multiWaveIn,noRecSplitsIn); //to perform validation checks!!
		    
			addRaceString = "insert into race values(?,?,?,?,?,?)"; 
			
			conn = cc1.openConnection();
       	    
			addRace = conn.prepareStatement(addRaceString);
			
			addRace.setInt(1, race1.getRaceId());
			addRace.setString(2, race1.getRaceName());
			addRace.setString(3, race1.getRaceLocation());
			//////////////////////////////////////////////////////
			java.util.Date utilDate = race1.getRaceDate();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			////////////////////////////////////////////////////////////////
			//addRace.setDate(4, (java.sql.Date)race1.getRaceDate());
			addRace.setDate(4, sqlDate);
			addRace.setBoolean(5, race1.getIsMultiWave());
			addRace.setInt(6, race1.getNoRecordedSplits());			
       	         	          	   							        	   	    	
			raceIdExists = validateRaceID(race1);
			
	        if(!raceIdExists)
	        {
	        	addRace.execute();
			    JOptionPane.showMessageDialog(null, "New race saved to database.");
	        }
	        else
	        {
	        	JOptionPane.showMessageDialog(null, "race with ID number :"+race1.getRaceId()+" already exists in database!!");
	        }  
			
	        rs1.close();
	        addRace.close();
	 }
	
	public boolean validateRaceID(Race race1) throws Exception
	{
			getRaceIdsString ="select race_id from race";
			conn = cc1.openConnection();
		    getRaceIds = conn.prepareStatement(getRaceIdsString);	       
		    rs1 = getRaceIds.executeQuery();
		    
		    raceIdExists = false;
		    while(rs1.next())
		    {
		    	if( race1.getRaceId()==rs1.getInt(1) ) 
		    		raceIdExists = true;
		    }
		    
		    return raceIdExists;
	        
	}
	
	 public int getHighestRaceId() throws Exception
	    {  		
		 	 getRaceIdsString ="select race_id from race";
		     conn = cc1.openConnection();
		     getRaceIds = conn.prepareStatement(getRaceIdsString);
		     rs1 = getRaceIds.executeQuery();
	    	 int highestRaceId=0;
	    	 int temp=0;
	    	 
	    	 while(rs1.next())
	    	 {
	    		  temp = rs1.getInt(1);
	    		  if (temp > highestRaceId)
	    			  highestRaceId = temp; 
	         }
	    	 rs1.close();
	    	 getRaceIds.close();
	    	 final int I = highestRaceId;	    	
	    	 return I;
	    }
	
}


