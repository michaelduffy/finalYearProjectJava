package databaseClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import classes.Athlete;


public class AthleteHandler 
{
	private Athlete athlete1;
	private Connection conn;
	
	private PreparedStatement getAthletes = null;	
	private String getAthletesString;
	
	private PreparedStatement deleteAth = null;	
	private String deleteAthString;
	
	private PreparedStatement editAth = null;	
	private String editAthString;
	
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
	
	
	
}


