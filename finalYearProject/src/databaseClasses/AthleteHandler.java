package databaseClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JOptionPane;

import classes.Athlete;
import classes.Director;
import classes.Race;


public class AthleteHandler 
{
	private Athlete athlete1;
	private Connection conn;
	
	private PreparedStatement getAthletes = null;	
	private String getAthletesString;
	
	//private PreparedStatement getAthId = null;	
	//private String getAthIdString;
	
	//private PreparedStatement getUserRaceIds = null;	
	//private String getUserRaceIdsString;
	
	//private PreparedStatement getUserRaces = null;	
	//private String getUserRacesString;
	
	private PreparedStatement deleteAth = null;	
	private String deleteAthString;
	
	private PreparedStatement editAth = null;	
	private String editAthString;
	
	//private PreparedStatement addAth = null;	
	//private String addAthString;
	
	//private PreparedStatement getAthIds = null;	
	//private String getAthIdsString;
	
	//private boolean athIdExists;
	
	private ResultSet rs1;
	private ConnectionClass cc1;
	
	public AthleteHandler(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn,
							String username, String userPass)
	{
		cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn);
		//athIdExists = false;
	}
	
	//method to return all Athletes in database
	public ResultSet getAthletes() throws Exception
	{
		
	    getAthletesString ="select * from series_athlete";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getAthletes = conn.prepareStatement(getAthletesString);
		rs1 = getAthletes.executeQuery();
									
		return rs1;					
	}
	
	/*public ResultSet getRacesByDirEmail(String email) throws Exception
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
	}*/
	
	//method to delete an athlete in database
	public int deleteAthlete(int athIdIn)throws Exception
	{
		int objectDeletedId = -1;
    	int deleteCheck = 0;
    	
		deleteAthString = "DELETE FROM series_athlete WHERE athlete_id = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		deleteAth =conn.prepareStatement(deleteAthString);
		deleteAth.setInt(1,athIdIn);
    	
    	deleteCheck = deleteAth.executeUpdate(); //should return 1 for successful delete otherwise 0;
    	if(deleteCheck !=0)
    	{
        	 objectDeletedId = athIdIn;
      	}
    	deleteAth.close();
    	final int I = objectDeletedId;
    	
    	return I;
	}
	
	//method to edit an athlete in database
	public int editAthlete( int idIn, String fNameIn,String lNameIn,String add1In,String add2In,String add3In,
							String countyIn,String genderIn,Date dobIn,String phoneIn,String emailIn,String passIn) throws Exception
    {
    	int editCheck = 0; //only 0 if update does not perform
    	
    	athlete1 = new Athlete(idIn,fNameIn,lNameIn,add1In,add2In,add3In,countyIn,genderIn,dobIn,phoneIn,emailIn,passIn); //to perform validation checks!!
    	editAthString = "UPDATE series_athlete SET ath_first_name = ?,"
    			           + "ath_last_name = ?,"
    			           + "address_line_1 = ?,"
    			           + "address_line_2 = ?,"
    			           + "address_line_3 = ?,"
    			           + "county = ?,"
    			           + "gender = ?,"
    			           + "date_of_birth = ?,"
    			           + "phone_num = ?,"
    			           + "email = ?,"
    			           + "password = ? WHERE athlete_id =?";
   			        
    	conn = cc1.openConnection();
    	editAth = conn.prepareStatement(editAthString);
    	
    	
    	editAth.setString(1, athlete1.getAthFname());
    	editAth.setString(2, athlete1.getAthLname());
    	editAth.setString(3, athlete1.getAddLine1());
    	editAth.setString(4, athlete1.getAddLine2());
    	editAth.setString(5, athlete1.getAddLine3());
    	editAth.setString(6, athlete1.getCounty());
    	editAth.setString(7, athlete1.getGender()+"");
    	editAth.setDate(8, (java.sql.Date) athlete1.getDob());
    	editAth.setString(9, athlete1.getPhoneNum());
    	editAth.setString(10, athlete1.getEmail());
    	editAth.setString(11, athlete1.getPassword());
    	editAth.setInt(12, athlete1.getAthId());
    	
    	
    	
    	
    	editCheck = editAth.executeUpdate(); //perform update and return confirmation 
    	editAth.close();
    	
    	final int I = editCheck;
    	
    	return I;
    }
	
	/*public  void addRace(int idIn, String nameIn,String locationIn,Date dateIn,Boolean multiWaveIn,
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
	 }*/
	
	/*public boolean validateRaceID(Race race1) throws Exception
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
	        
	}*/
	
	/* public int getHighestRaceId() throws Exception
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
	    } */
	
}


